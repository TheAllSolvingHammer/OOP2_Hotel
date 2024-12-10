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
    @Override
    public T save(T t) {
        Transaction transaction = null;
        try
        {
            Session session = HibernateUtil.openSession();
            transaction = session.beginTransaction();
            if(!getAll().contains(t) || getAll().isEmpty()){
                session.persist(t);
            }
            else session.merge(t);
            transaction.commit();
            session.close();
            return t;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public T delete(T t) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.openSession()) {
            transaction = session.beginTransaction();

            session.remove(t);

            transaction.commit();
            return t;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    @Override
    public List<T> getAll() {
        try (Session session = HibernateUtil.openSession()) {
            return session.createQuery("FROM " + entityClass.getName(), entityClass).list();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all entities.", e);
        }
    }

    @Override
    public Optional<T> findById(E id) {
        try (Session session = HibernateUtil.openSession()) {
            T entity = session.get(entityClass, id);
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve entity with id: " + id, e);
        }
    }
}
