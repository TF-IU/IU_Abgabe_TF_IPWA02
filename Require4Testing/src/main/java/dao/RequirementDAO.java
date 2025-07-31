package dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import entity.Requirement;

@ApplicationScoped
public class RequirementDAO {

	@Inject
	private EntityManager em;


    public List<Requirement> findAll() {
        return em.createQuery("SELECT r FROM Requirement r", Requirement.class).getResultList();
    }

    public Requirement findById(int id) {
        return em.find(Requirement.class, id);
    }
    
    public boolean titleExists(String title) {
        return !em
            .createQuery("SELECT r FROM Requirement r WHERE r.title = :title", Requirement.class)
            .setParameter("title", title)
            .getResultList()
            .isEmpty();
    }

    
    public void create(Requirement r) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(r);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }

    }


    public Requirement update(Requirement r) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Requirement updated = em.merge(r);
            tx.commit();
            return updated;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }


    public void delete(Requirement r) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Requirement managed = em.merge(r); 
            em.remove(managed);               
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    

}