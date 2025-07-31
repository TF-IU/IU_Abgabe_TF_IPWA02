package dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import entity.TestCase;
import enums.TestResult;

@ApplicationScoped
public class TestCaseDAO {

    @Inject
    private EntityManager em;

    public List<TestCase> findAll() {
        return em.createQuery("SELECT t FROM TestCase t", TestCase.class).getResultList();
    }

    public TestCase findById(int id) {
        return em.find(TestCase.class, id);
    }
    
    //Count the number of test cases by their result type
    public Map<TestResult, Long> countByResult() {
        List<Object[]> results = em.createQuery(
            "SELECT t.result, COUNT(t) FROM TestCase t GROUP BY t.result", Object[].class
        ).getResultList();

        Map<TestResult, Long> resultMap = new EnumMap<>(TestResult.class);
        for (Object[] row : results) {
            resultMap.put((TestResult) row[0], (Long) row[1]);
        }
        return resultMap;
    }


    public void create(TestCase t) {
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

    public TestCase update(TestCase t) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            TestCase updated = em.merge(t);
            tx.commit();
            return updated;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void delete(TestCase t) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            TestCase managed = em.merge(t);
            em.remove(managed);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
}