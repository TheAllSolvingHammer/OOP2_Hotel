package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.persistence.connection.HibernateUtil;
import com.tuvarna.hotel.persistence.entities.HotelEntity;
import com.tuvarna.hotel.persistence.entities.UserEntity;
import com.tuvarna.hotel.persistence.enums.RoleEntity;
import com.tuvarna.hotel.persistence.repositories.HotelRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Singleton
public class HotelRepositoryImpl extends BaseRepositoryImpl<HotelEntity,UUID> implements HotelRepository<HotelEntity,UUID> {

    public HotelRepositoryImpl() {
        super(HotelEntity.class);
    }


    public Optional<HotelEntity> findHotelById(UUID id) {
        Session session = HibernateUtil.openSession();
        try {
            HotelEntity hotel = session.get(HotelEntity.class, id);
            return Optional.ofNullable(hotel);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve hotel with id: " + id, e);
        } finally {
            session.close();
        }
    }
    @Override
    public List<HotelEntity> findAllByOwner(UserEntity owner) {
        Session session = HibernateUtil.openSession();
        try {
            String hql = "SELECT h FROM HotelEntity h JOIN h.userList u WHERE u = :owner";
            Query<HotelEntity> query = session.createQuery(hql, HotelEntity.class);
            query.setParameter("owner", owner);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve hotels for the given owner: " + owner.getId(), e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<UserEntity> findManagersWithHotels() {
        Session session = HibernateUtil.openSession();
        try  {
            Query<UserEntity> query = session.createQuery(
                    "SELECT DISTINCT u FROM UserEntity u " +
                            "JOIN FETCH u.hotelList h " +
                            "WHERE u.role = :role", UserEntity.class
            );
            query.setParameter("role", RoleEntity.MANAGER);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch managers with their hotels", e);
        }
        finally {
            session.close();
        }
    }

    @Override
    public List<HotelEntity> findAllHotelsWithUsers() {
        Session session = HibernateUtil.openSession();
        try {
            Query<HotelEntity> query = session.createQuery(
                    "SELECT DISTINCT h FROM HotelEntity h " +
                            "JOIN FETCH h.userList u", HotelEntity.class
            );
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch hotels with their users", e);
        }finally {
            session.close();
        }
    }
}
