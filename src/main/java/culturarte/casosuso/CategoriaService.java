
package culturarte.casosuso;

import culturarte.casosuso.JPAUtil;
import culturarte.casosuso.Categoria;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CategoriaService {

    public List<Categoria> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            // fetch one level of children to avoid LazyInitializationException
            TypedQuery<Categoria> q = em.createQuery("SELECT DISTINCT c FROM Categoria c LEFT JOIN FETCH c.hijos", Categoria.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    public Categoria save(Categoria c) {
        javax.persistence.EntityManager em = culturarte.casosuso.JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            if (c.getId() == null) em.persist(c); else c = em.merge(c);
            em.getTransaction().commit();
            return c;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

}
