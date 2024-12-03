package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.persistence.connection.HibernateUtil;
import com.tuvarna.hotel.persistence.entities.UserEntity;
import com.tuvarna.hotel.persistence.repositories.UserRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Singleton
public class UserRepositoryImpl  implements UserRepository<UserEntity,UUID> {


    @Override
    public Optional<UserEntity> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<UserEntity> getAll() {
        return List.of();
    }

    @Override
    public UserEntity save(UserEntity t) {
        Transaction transaction = null;
        try
        {
            Session session = HibernateUtil.openSession();
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
    public UserEntity delete(UserEntity userEntity) {
        return null;
    }
}
