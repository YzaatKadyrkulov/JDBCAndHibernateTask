package peaksoft.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.HibernateException;
import peaksoft.model.User;
import peaksoft.util.Util;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public static final EntityManagerFactory entityManagerFactory = Util.getEntityManagerFactory();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
    }

    @Override
    public void dropUsersTable() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery("drop table users").executeUpdate();
            entityManager.getTransaction().commit();
            System.out.println("Success");
        } catch (HibernateException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        if (entityManagerFactory != null) {
            try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
                entityManager.getTransaction().begin();
                User user = new User(name, lastName, age);
                entityManager.persist(user);
                entityManager.getTransaction().commit();
                System.out.println("Success");
            } catch (HibernateException e) {
                System.err.println(e.getMessage());
            }
        } else {
            System.err.println("EntityManagerFactory is not initialized.");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.createQuery("delete from User c where c.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            assert entityManagerFactory != null;
            try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
                entityManager.getTransaction().commit();
                entityManager.createNativeQuery("select  u from User u")
                        .executeUpdate();
                entityManager.getTransaction().commit();
            }
        } catch (HibernateException e) {
            System.err.println(e.getMessage());
        }
        return users;
    }


    @Override
    public void cleanUsersTable() {
        try {
            assert entityManagerFactory != null;
            try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
                entityManager.getTransaction().commit();
                entityManager.createNativeQuery("truncate table User")
                        .executeUpdate();
                entityManager.getTransaction().commit();
                System.out.println("Successfully cleaned");
            }
        } catch (HibernateException e) {
            System.err.println(e.getMessage());
        }
    }
}