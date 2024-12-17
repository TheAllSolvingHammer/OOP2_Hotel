package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.persistence.connection.HibernateUtil;
import com.tuvarna.hotel.persistence.contracts.EntityMarker;
import com.tuvarna.hotel.persistence.repositories.BaseRepository;
import lombok.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class BaseRepositoryImpl<T extends EntityMarker,E extends UUID> implements BaseRepository<T,E> {
    private final Class<T> entityClass;
//    @Override
//    public void save(T t) {
//        Session session = HibernateUtil.openSession();
//        Transaction transaction = null;
//        try {
//            transaction = session.beginTransaction();
//            UUID id = t.getId();
//            if (id != null && existsById(id)) {
//                session.merge(t);
//            } else {
//                session.persist(t);
//            }
//
//            transaction.commit();
//
////            return t;
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            throw new RuntimeException("Failed to save entity", e);
//        }
//        finally {
//            session.close();
//        }
//
//    }
    @Override
    public void save(T t) {
        Session session = HibernateUtil.openSession();
        Transaction transaction = null;
        try
        {
            transaction = session.beginTransaction();
            if(!getAll().contains(t) || getAll().isEmpty()){
                session.persist(t);
            }
            else session.merge(t);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to save entity", e);
        }finally {
            session.close();
        }
    }

    @Override
    public T delete(T t) {
        Session session = HibernateUtil.openSession();
        Transaction transaction = null;
        try  {
            transaction = session.beginTransaction();

            session.remove(t);

            transaction.commit();
            return t;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
        finally {
            session.close();
        }
    }

    @Override
    public List<T> getAll() {
        Session session = HibernateUtil.openSession();
        try  {
            return session.createQuery("FROM " + entityClass.getSimpleName(), entityClass).list();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all entities.", e);
        }
        finally {
            session.close();
        }
    }

    @Override
    public Optional<T> findById(UUID id) {
        Session session = HibernateUtil.openSession();
        try  {
            T entity = session.get(entityClass, id);
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve entity with id: " + id, e);
        }
        finally {
            session.close();
        }
    }
    @Override
    public boolean existsById(UUID id) {
        Session session = HibernateUtil.openSession();
        try {
            String query = "SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e WHERE e.id = :id";
            Long count = session.createQuery(query, Long.class)
                    .setParameter("id", id)
                    .uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            throw new RuntimeException("Failed to check entity existence with id: " + id, e);
        }
        finally {
            session.close();
        }
    }
}
