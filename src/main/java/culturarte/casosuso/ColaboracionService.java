package culturarte.casosuso;

import culturarte.casosuso.JPAUtil;
import culturarte.casosuso.Colaboracion;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Servicio para operaciones sobre Colaboracion.
 * Asume que las claves primarias (ids) son String (p. ej. "Col01").
 */
public class ColaboracionService {

    public Colaboracion save(Colaboracion c) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            if (c.getId() == null) {
                em.persist(c);
            } else {
                c = em.merge(c);
            }
            em.getTransaction().commit();
            return c;
        } finally {
            if (em.isOpen()) em.close();
        }
    }

    public List<Colaboracion> findByColaboradorId(String colaboradorId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Colaboracion> q = em.createQuery("SELECT c FROM Colaboracion c WHERE c.colaborador.id = :id", Colaboracion.class);
            q.setParameter("id", colaboradorId);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Colaboracion> findByPropuestaRef(String propuestaRef) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Colaboracion> q = em.createQuery("SELECT c FROM Colaboracion c WHERE c.propuesta.id = :ref OR c.propuesta.id = :ref", Colaboracion.class);
            q.setParameter("ref", propuestaRef);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    
    public List<Colaboracion> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Colaboracion> q = em.createQuery("SELECT c FROM Colaboracion c", Colaboracion.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }


    public Colaboracion findById(String id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(Colaboracion.class, id);
        } finally {
            em.close();
        }
    }

public void deleteById(String id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            Colaboracion c = em.find(Colaboracion.class, id);
            if (c != null) em.remove(c);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
