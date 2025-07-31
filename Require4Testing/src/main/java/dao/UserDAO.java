package dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import entity.User;
import enums.Role;

@ApplicationScoped
public class UserDAO {

    @Inject
    private EntityManager em;

    public User findById(int id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class)
                 .getResultList();
    }
    
	public User findByName(String name) {
		try {
			return em.createQuery("SELECT u FROM User u WHERE u.name = :name", User.class)
					.setParameter("name", name)
					.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<User> findByRole(Role role) {
	    return em.createQuery(
	        "SELECT u FROM User u WHERE :role MEMBER OF u.roles", User.class)
	        .setParameter("role", role)
	        .getResultList();
	}



    public void create(User user) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(user);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public User update(User user) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            User updated = em.merge(user);
            tx.commit();
            return updated;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void delete(User user) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            User managed = em.contains(user) ? user : em.merge(user);
            em.remove(managed);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

}
