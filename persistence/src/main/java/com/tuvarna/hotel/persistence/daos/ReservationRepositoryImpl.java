package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.persistence.connection.HibernateUtil;
import com.tuvarna.hotel.persistence.dtos.ReceptionistReservationDTO;
import com.tuvarna.hotel.persistence.dtos.RoomUsageDTO;
import com.tuvarna.hotel.persistence.dtos.ServiceUsageDTO;
import com.tuvarna.hotel.persistence.entities.ReservationEntity;
import com.tuvarna.hotel.persistence.entities.RoomEntity;
import com.tuvarna.hotel.persistence.repositories.ReservationRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDate;
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

    @Override
    public List<ServiceUsageDTO> countServiceUsage(UUID hotelId,LocalDate startDate, LocalDate endDate) {
        String hql = """
        SELECT new com.tuvarna.hotel.persistence.dtos.ServiceUsageDTO(
            s.serviceName,
            COUNT(s),
            SUM(s.price)
        )
        FROM ReservationEntity r
        JOIN r.services s
        JOIN r.room rm
        JOIN rm.hotel h
        WHERE h.id = :hotelId
          AND r.startDate >= :startDate
          AND r.endDate <= :endDate
        GROUP BY s.serviceName
    """;
        Session session = HibernateUtil.openSession();
        try {
            Query<ServiceUsageDTO> query = session.createQuery(hql, ServiceUsageDTO.class);
            query.setParameter("hotelId", hotelId);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error getting service usage and revenue by hotel", e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<ReceptionistReservationDTO> getReservationsByReceptionists(UUID hotelId, LocalDate startDate, LocalDate endDate) {
        String hql = """
        SELECT new com.tuvarna.hotel.persistence.dtos.ReceptionistReservationDTO(
            r.id, CONCAT(u.firstName, ' ', u.lastName), h.id
        )
        FROM ReservationEntity r
        JOIN r.createdBy u
        JOIN r.room rm
        JOIN rm.hotel h
        WHERE h.id = :hotelId
          AND r.startDate >= :startDate
          AND r.endDate <= :endDate
        ORDER BY u.lastName, u.firstName
    """;
        Session session = HibernateUtil.openSession();
        try  {
            Query<ReceptionistReservationDTO> query = session.createQuery(hql, ReceptionistReservationDTO.class);
            query.setParameter("hotelId", hotelId);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error getting query", e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<RoomUsageDTO> getRoomUsageByHotelAndDate(UUID hotelId, LocalDate startDate, LocalDate endDate) {
        String hql = """
        SELECT new com.tuvarna.hotel.persistence.dtos.RoomUsageDTO(
            rm.roomNumber,
            rm.price,
            rm.type,
            COUNT(r.id)
        )
        FROM RoomEntity rm
        LEFT JOIN ReservationEntity r ON rm.id = r.room.id
        WHERE rm.hotel.id = :hotelId
          AND (:startDate IS NULL OR r.startDate >= :startDate)
          AND (:endDate IS NULL OR r.endDate <= :endDate)
        GROUP BY rm.roomNumber, rm.floor, rm.price, rm.type
    """;
        Session session = HibernateUtil.openSession();
        try {
            Query<RoomUsageDTO> query = session.createQuery(hql, RoomUsageDTO.class);
            query.setParameter("hotelId", hotelId);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving room usage by hotel and date", e);
        } finally {
            session.close();
        }
    }

    public List<ReservationEntity> getReservationsByHotelAndDate(UUID hotelId, LocalDate startDate, LocalDate endDate) {
        String hql = """
        SELECT r
        FROM ReservationEntity r
        JOIN r.room rm
        JOIN rm.hotel h
        WHERE h.id = :hotelId
          AND (r.startDate >= :startDate)
          AND (r.endDate <= :endDate)
    """;
        Session session = HibernateUtil.openSession();
        try {
            Query<ReservationEntity> query = session.createQuery(hql, ReservationEntity.class);
            query.setParameter("hotelId", hotelId);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching reservations by hotel and date range", e);
        } finally {
            session.close();
        }

    }

    @Override
    public List<ReservationEntity> findAllByHotelId(UUID hotelId) {
        String hql = """
        SELECT r
        FROM ReservationEntity r
        JOIN r.room rm
        JOIN rm.hotel h
        WHERE h.id = :hotelId
    """;
        Session session = HibernateUtil.openSession();
        try {
            Query<ReservationEntity> query = session.createQuery(hql, ReservationEntity.class);
            query.setParameter("hotelId", hotelId);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching reservations by hotel", e);
        } finally {
            session.close();
        }
    }

}
