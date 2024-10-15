package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.persistence.contracts.EntityMarker;
import com.tuvarna.hotel.persistence.repositories.BaseRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@AllArgsConstructor
@RequiredArgsConstructor
public class BaseRepositoryImpl<T extends EntityMarker,E extends UUID> implements BaseRepository<T,E> {
    private SessionFactory sessionFactory;
    private Class<T> entityClass;


    @Override
    public Optional<T> findById(E id) {
        Session session = sessionFactory.openSession();
        return Optional.of(session.get(entityClass, id));
    }

    @Override
    public List<T> getAll() {
        Session session = sessionFactory.openSession();
        return session.createQuery("FROM " + entityClass.getName(), entityClass).list();
    }

    @Override
    public T save(T t) {
        Transaction transaction = null;
        try
        {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            if(!getAll().contains(t) || getAll().isEmpty()){
                session.persist(t);
            }
            //todo is==null?
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
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.remove(t);
            transaction.commit();
            return t;
        }  catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }


}
