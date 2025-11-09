package com.renato.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.renato.config.HibernateConfig;
import java.util.List;

public abstract class GenericRepository<T> {
    private final Class<T> entityClass;

    protected GenericRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T save(T entity) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        }
        catch (Exception e) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                }
                catch (Exception rbEx) {
                    // ignore rollback errors but log
                    rbEx.printStackTrace();
                }
            }
            e.printStackTrace();
            throw e;
        }
    }

    public T update(T entity) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
            return entity;
        }
        catch (Exception e) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                }
                catch (Exception rbEx) {
                    rbEx.printStackTrace();
                }
            }
            e.printStackTrace();
            throw e;
        }
    }

    public void delete(T entity) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                }
                catch (Exception rbEx) {
                    rbEx.printStackTrace();
                }
            }
            e.printStackTrace();
            throw e;
        }
    }

    public T findById(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.get(entityClass, id);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<T> findAll() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery("from " + entityClass.getName(), entityClass).list();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}