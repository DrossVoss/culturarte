package culturarte.casosuso;

import culturarte.casosuso.JPAUtil;
import culturarte.casosuso.Follow;
import culturarte.casosuso.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * FollowService - provides follow/unfollow and lookup.
 * Tries to use normalized JPA entity 'Follow' / table 'follows' first.
 * If the normalized table is not present (legacy DB), it falls back to reading/writing
 * the legacy wide 'seguidores' table via JDBC and builds Usuario lists.
 */
public class FollowService {

    public Follow follow(Usuario seguidor, Usuario seguido) {
        try {
            EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
            try {
                em.getTransaction().begin();
                Follow f = new Follow();
                f.setSeguidor(seguidor);
                f.setSeguido(seguido);
                em.persist(f);
                em.getTransaction().commit();
                return f;
            } finally {
                if (em != null && em.isOpen()) em.close();
            }
        } catch (Exception ex) {
            // fallback to legacy 'seguidores' table
            try (Connection conn = DatabaseConnection.getConnection()) {
                String col = seguido.getId();
                String sql = "UPDATE seguidores SET `" + col + "` = ? WHERE sigue = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, "X");
                    ps.setString(2, seguidor.getId());
                    int updated = ps.executeUpdate();
                    if (updated == 0) {
                        // maybe no row for seguidor yet -> insert row
                        // Build dynamic insert: we will insert a row with sigue and set this column to X
                        String insert = "INSERT INTO seguidores (sigue, `" + col + "`) VALUES (?, 'X')";
                        try (PreparedStatement ps2 = conn.prepareStatement(insert)) {
                            ps2.setString(1, seguidor.getId());
                            ps2.executeUpdate();
                        }
                    }
                }
            } catch (Exception ex2) {
                throw new RuntimeException("Follow failed (JPA+legacy): " + ex.getMessage() + " / " + ex2.getMessage(), ex2);
            }
            // Return a minimal Follow object (not managed)
            Follow f = new Follow();
            f.setSeguidor(seguidor);
            f.setSeguido(seguido);
            return f;
        }
    }

    public void unfollow(Usuario seguidor, Usuario seguido) {
        try {
            EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
            try {
                em.getTransaction().begin();
                TypedQuery<Follow> q = em.createQuery("SELECT f FROM Follow f WHERE f.seguidor.id = :s AND f.seguido.id = :t", Follow.class);
                q.setParameter("s", seguidor.getId());
                q.setParameter("t", seguido.getId());
                List<Follow> res = q.getResultList();
                for (Follow f : res) {
                    em.remove(em.contains(f) ? f : em.merge(f));
                }
                em.getTransaction().commit();
            } finally {
                if (em != null && em.isOpen()) em.close();
            }
        } catch (Exception ex) {
            // fallback to legacy
            try (Connection conn = DatabaseConnection.getConnection()) {
                String col = seguido.getId();
                String sql = "UPDATE seguidores SET `" + col + "` = NULL WHERE sigue = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, seguidor.getId());
                    ps.executeUpdate();
                }
            } catch (Exception ex2) {
                throw new RuntimeException("Unfollow failed (JPA+legacy): " + ex.getMessage() + " / " + ex2.getMessage(), ex2);
            }
        }
    }

    public List<Usuario> findSeguidos(Usuario seguidor) {
        // Try JPA first
        try {
            EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
            try {
                TypedQuery<Follow> q = em.createQuery("SELECT f FROM Follow f WHERE f.seguidor.id = :s", Follow.class);
                q.setParameter("s", seguidor.getId());
                List<Follow> res = q.getResultList();
                List<Usuario> out = new ArrayList<>();
                for (Follow f : res) out.add(f.getSeguido());
                return out;
            } finally {
                if (em != null && em.isOpen()) em.close();
            }
        } catch (Exception ex) {
            // fallback to legacy 'seguidores' wide table
            List<Usuario> out = new ArrayList<>();
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "SELECT * FROM seguidores WHERE sigue = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, seguidor.getId());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            ResultSetMetaData meta = rs.getMetaData();
                            for (int i = 1; i <= meta.getColumnCount(); i++) {
                                String col = meta.getColumnName(i);
                                if (col.equalsIgnoreCase("sigue")) continue;
                                String val = rs.getString(i);
                                if (val != null && !val.trim().isEmpty()) {
                                    // col name is the id of followed user
                                    java.util.Optional<Usuario> ou = new UsuarioService().findById(col);
                                    if (ou.isPresent()) out.add(ou.get());
                                }
                            }
                        }
                    }
                }
            } catch (Exception ex2) {
                throw new RuntimeException("findSeguidos failed (JPA+legacy): " + ex.getMessage() + " / " + ex2.getMessage(), ex2);
            }
            return out;
        }
    }

    public List<Usuario> findSeguidores(Usuario seguido) {
        try {
            EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
            try {
                TypedQuery<Follow> q = em.createQuery("SELECT f FROM Follow f WHERE f.seguido.id = :t", Follow.class);
                q.setParameter("t", seguido.getId());
                List<Follow> res = q.getResultList();
                List<Usuario> out = new ArrayList<>();
                for (Follow f : res) out.add(f.getSeguidor());
                return out;
            } finally {
                if (em != null && em.isOpen()) em.close();
            }
        } catch (Exception ex) {
            List<Usuario> out = new ArrayList<>();
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "SELECT * FROM seguidores";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    try (ResultSet rs = ps.executeQuery()) {
                        ResultSetMetaData meta = rs.getMetaData();
                        int colCount = meta.getColumnCount();
                        // Find column index for the 'seguido' id
                        int targetIdx = -1;
                        for (int i = 1; i <= colCount; i++) {
                            if (meta.getColumnName(i).equalsIgnoreCase(seguido.getId())) {
                                targetIdx = i;
                                break;
                            }
                        }
                        if (targetIdx == -1) return out;
                        // iterate rows, first column is 'sigue'
                        while (rs.next()) {
                            String val = rs.getString(targetIdx);
                            if (val != null && !val.trim().isEmpty()) {
                                String seguidorId = rs.getString("sigue");
                                java.util.Optional<Usuario> ou = new UsuarioService().findById(seguidorId);
                                    if (ou.isPresent()) out.add(ou.get());
                            }
                        }
                    }
                }
            } catch (Exception ex2) {
                throw new RuntimeException("findSeguidores failed (JPA+legacy): " + ex.getMessage() + " / " + ex2.getMessage(), ex2);
            }
            return out;
        }
    }
}
