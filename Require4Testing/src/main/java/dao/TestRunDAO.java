package dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import entity.TestRun;

@ApplicationScoped
public class TestRunDAO {

    @Inject
    private EntityManager em;

    public List<TestRun> findAll() {
        return em.createQuery("SELECT t FROM TestRun t", TestRun.class).getResultList();
    }

    public TestRun findById(int id) {
        return em.find(TestRun.class, id);
    }

    public void create(TestRun t) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(t);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public TestRun update(TestRun t) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            TestRun updated = em.merge(t);
            tx.commit();
            return updated;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void delete(TestRun t) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            TestRun managed = em.merge(t);
            em.remove(managed);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
}