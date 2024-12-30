package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.persistence.connection.HibernateUtil;
import com.tuvarna.hotel.persistence.entities.HotelEntity;
import com.tuvarna.hotel.persistence.entities.ReservationEntity;
import com.tuvarna.hotel.persistence.entities.RoomEntity;
import com.tuvarna.hotel.persistence.repositories.ReservationRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
@Singleton
public class ReservationRepositoryImpl extends BaseRepositoryImpl<ReservationEntity,UUID> implements ReservationRepository<ReservationEntity,UUID> {


    public ReservationRepositoryImpl() {
        super(ReservationEntity.class);
    }

    @Override
    public Boolean isRoomAvailable(RoomEntity room, LocalDate startDate, LocalDate endDate) {
        Session session = HibernateUtil.openSession();
        try {
            String hql = """
            SELECT COUNT(r)
            FROM ReservationEntity r
            WHERE r.room = :room
            AND (:startDate BETWEEN r.startDate AND r.endDate
                 OR :endDate BETWEEN r.startDate AND r.endDate
                 OR r.startDate BETWEEN :startDate AND :endDate)
            """;
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("room", room);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            return query.uniqueResult() == 0;
        } catch (Exception e) {
            throw new RuntimeException("Error checking room availability", e);
        } finally {
            session.close();
        }
    }
}
