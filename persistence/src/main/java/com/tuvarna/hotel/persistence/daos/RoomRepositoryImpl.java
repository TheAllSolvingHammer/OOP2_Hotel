package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.persistence.connection.HibernateUtil;
import com.tuvarna.hotel.persistence.entities.HotelEntity;
import com.tuvarna.hotel.persistence.entities.RoomEntity;
import com.tuvarna.hotel.persistence.repositories.RoomRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.UUID;

@Singleton
public class RoomRepositoryImpl extends BaseRepositoryImpl<RoomEntity,UUID> implements RoomRepository<RoomEntity,UUID> {


    public RoomRepositoryImpl() {
        super(RoomEntity.class);
    }

    @Override
    public List<RoomEntity> findAllByHotel(HotelEntity hotelEntity) {
        Session session = HibernateUtil.openSession();
        try {
            String hql = "FROM RoomEntity r WHERE r.hotel = :hotel";
            Query<RoomEntity> query = session.createQuery(hql, RoomEntity.class);
            query.setParameter("hotel", hotelEntity);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch rooms for hotel: " + hotelEntity.getId(), e);
        } finally {
            session.close();
        }
    }
}
