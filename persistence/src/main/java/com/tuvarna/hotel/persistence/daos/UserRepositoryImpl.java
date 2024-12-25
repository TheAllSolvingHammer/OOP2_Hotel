package com.tuvarna.hotel.persistence.daos;


import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.persistence.connection.HibernateUtil;
import com.tuvarna.hotel.persistence.entities.UserEntity;
import com.tuvarna.hotel.persistence.enums.RoleEntity;
import com.tuvarna.hotel.persistence.repositories.UserRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
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

    @Override
    public List<UserEntity> findAllOwners() {
        Session session = HibernateUtil.openSession();
        try {
            String hql = "FROM UserEntity u WHERE u.role = :role";
            Query<UserEntity> query = session.createQuery(hql, UserEntity.class);
            query.setParameter("role", RoleEntity.OWNER);

            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get owners.", e);
        }
        finally {
            session.close();
        }
    }

    @Override
    public List<UserEntity> findAllManagers() {
        Session session = HibernateUtil.openSession();
        try {
            String hql = "FROM UserEntity u WHERE u.role = :role";
            Query<UserEntity> query = session.createQuery(hql, UserEntity.class);
            query.setParameter("role", RoleEntity.MANAGER);

            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get managers.", e);
        }
        finally {
            session.close();
        }
    }

    @Override
    public List<UserEntity> findAllReceptionist() {
        Session session = HibernateUtil.openSession();
        try {
            String hql = "FROM UserEntity u WHERE u.role = :role";
            Query<UserEntity> query = session.createQuery(hql, UserEntity.class);
            query.setParameter("role", RoleEntity.EMPLOYEE);

            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get receptionists.", e);
        }
        finally {
            session.close();
        }
    }

    @Override
    public Optional<UserEntity> findByID(UUID uuid) {
        Session session = HibernateUtil.openSession();
        try {
            String hql = "FROM " + getEntityClass().getName() + " u WHERE u.id = :uuid";
            Query<UserEntity> query = session.createQuery(hql, getEntityClass());
            query.setParameter("uuid", uuid);
            UserEntity user = query.uniqueResult();
            return Optional.ofNullable(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve user with UUID: " + uuid, e);
        } finally {
            session.close();
        }
    }

    public List<UserEntity> findAllUnassignedOwners() {
        Session session = HibernateUtil.openSession();
        try {
            String hql = "SELECT u FROM UserEntity u WHERE u.role = :role AND u NOT IN (SELECT hu FROM HotelEntity h JOIN h.userList hu)";
            Query<UserEntity> query = session.createQuery(hql, UserEntity.class);
            query.setParameter("role", RoleEntity.OWNER);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve unassigned owners.", e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<UserEntity> findAllUnassignedManagers() {
        Session session = HibernateUtil.openSession();
        try {
            String hql = "SELECT u FROM UserEntity u WHERE u.role = :role AND u NOT IN (SELECT hu FROM HotelEntity h JOIN h.userList hu)";
            Query<UserEntity> query = session.createQuery(hql, UserEntity.class);
            query.setParameter("role", RoleEntity.MANAGER);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve unassigned managers.", e);
        } finally {
            session.close();
        }
    }
}
