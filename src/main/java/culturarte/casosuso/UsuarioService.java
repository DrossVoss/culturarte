package culturarte.casosuso;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.Query;
import java.time.LocalDateTime;

/**
 * UsuarioService - maneja persistencia de Usuarios con control de transacciones y validaciones básicas.
 */
public class UsuarioService {

    public Usuario save(Usuario u) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            // Ensure an id exists for new users
            if (u.getId() == null || u.getId().trim().isEmpty()) {
                String gen = UUID.randomUUID().toString().replaceAll("-", "").substring(0,8).toUpperCase();
                u.setId(gen);
            }

            // Normalize tipo to single char expected by DB (CHAR(1))
            if (u.getTipo() != null) {
                String t = u.getTipo().trim().toUpperCase();
                if (t.startsWith("PROP")) u.setTipo("P");
                else if (t.startsWith("COL")) u.setTipo("C");
                else if (t.length() > 1) u.setTipo(t.substring(0,1));
            }

            if (u.getCreadoEn() == null) u.setCreadoEn(LocalDateTime.now());

            em.getTransaction().begin();
            Usuario managed = em.merge(u);
            em.getTransaction().commit();

            // After saving Usuario, also persist biografia (table biografias) and detalles de proponente (detalles_proponentes)
            try {
                if (u.getBiografia() != null && !u.getBiografia().trim().isEmpty()) {
                    em.getTransaction().begin();
                    Biografia b = em.find(Biografia.class, u.getId());
                    if (b == null) {
                        b = new Biografia();
                        b.setUsuarioId(u.getId());
                        b.setTexto(u.getBiografia());
                        em.persist(b);
                    } else {
                        b.setTexto(u.getBiografia());
                        em.merge(b);
                    }
                    em.getTransaction().commit();
                }
            } catch (Throwable t) {
                try { if (em.getTransaction().isActive()) em.getTransaction().rollback(); } catch (Exception ex) {}
            }

            try {
                String img = u.getImgPath();
                String dir = u.getDireccion();
                String web = u.getWeb();
                if ((img != null && !img.trim().isEmpty()) || (dir != null && !dir.trim().isEmpty()) || (web != null && !web.trim().isEmpty())) {
                    String sql = "INSERT INTO detalles_proponentes (usuario_id, imagen_url, direccion, sitio_web) VALUES (:id, :img, :dir, :web) " +
                                 "ON DUPLICATE KEY UPDATE imagen_url = :img, direccion = :dir, sitio_web = :web";
                    em.getTransaction().begin();
                    Query q = em.createNativeQuery(sql);
                    q.setParameter("id", u.getId());
                    q.setParameter("img", img);
                    q.setParameter("dir", dir);
                    q.setParameter("web", web);
                    q.executeUpdate();
                    em.getTransaction().commit();
                }
            } catch (Throwable t) {
                try { if (em.getTransaction().isActive()) em.getTransaction().rollback(); } catch (Exception ex) {}
            }

            return managed;
        } finally {
            em.close();
        }
    }

    

    public List<Usuario> findByTipo(String tipo) {
        if (tipo == null) return java.util.Collections.emptyList();
        String t = tipo.trim().toUpperCase();
        if (t.startsWith("PROP")) t = "P";
        else if (t.startsWith("COL")) t = "C";
        else if (t.length() > 1) t = t.substring(0,1);

        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Usuario> q = em.createQuery("SELECT u FROM Usuario u WHERE u.tipo = :tipo", Usuario.class);
            q.setParameter("tipo", t);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
public Optional<Usuario> findById(String id) {
        if (id == null) return Optional.empty();
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Usuario u = em.find(Usuario.class, id);
            if (u == null) return Optional.empty();

            // load biography if present
            try {
                Biografia b = em.find(Biografia.class, id);
                if (b != null) {
                    u.setBiografia(b.getTexto());
                }
            } catch (Throwable t) {
                // ignore
            }

            // try to load image URL from detalles_proponentes table
            try {
                List<?> res = em.createNativeQuery("SELECT imagen_url FROM detalles_proponentes WHERE usuario_id = :id")
                        .setParameter("id", id)
                        .getResultList();
                if (res != null && !res.isEmpty() && res.get(0) != null) {
                    String img = res.get(0).toString();
                    u.setImgPath(img);
                }
            } catch (Throwable t) {
                // ignore
            }

            return Optional.ofNullable(u);
        } finally {
            em.close();
        }
    }
}
