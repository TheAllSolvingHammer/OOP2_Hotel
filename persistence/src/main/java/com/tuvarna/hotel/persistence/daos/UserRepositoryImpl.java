package com.tuvarna.hotel.persistence.daos;


import com.tuvarna.hotel.persistence.connection.HibernateUtil;
import com.tuvarna.hotel.persistence.entities.UserEntity;
import com.tuvarna.hotel.persistence.repositories.UserRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Optional;
import java.util.UUID;

public class UserRepositoryImpl extends BaseRepositoryImpl<UserEntity,UUID>  implements UserRepository<UserEntity,UUID> {


    public UserRepositoryImpl() {
        super(UserEntity.class);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        Session session = HibernateUtil.openSession();
        try
        {
            String hql = "FROM " + getEntityClass().getName() + " u WHERE u.username = :username";
            Query<UserEntity> query = session.createQuery(hql, getEntityClass());
            query.setParameter("username", username);
            UserEntity user = query.uniqueResult();
            return Optional.ofNullable(user);
        }
         catch (Exception e) {
            throw new RuntimeException("Failed to retrieve user with username: " + username, e);
        }
        finally {
            session.close();
        }
    }
}
