package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.persistence.entities.UserEntity;
import com.tuvarna.hotel.persistence.repositories.UserRepository;
import org.hibernate.SessionFactory;

import java.util.UUID;

public class UserRepositoryImpl extends BaseRepositoryImpl<UserEntity, UUID> implements UserRepository {
    public UserRepositoryImpl(SessionFactory sessionFactory, Class<UserEntity> entityClass) {
        super(sessionFactory, entityClass);
    }
}
