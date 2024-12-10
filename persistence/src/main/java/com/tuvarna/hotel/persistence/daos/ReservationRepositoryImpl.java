package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.persistence.connection.HibernateUtil;
import com.tuvarna.hotel.persistence.entities.HotelEntity;
import com.tuvarna.hotel.persistence.entities.ReservationEntity;
import com.tuvarna.hotel.persistence.entities.RoomEntity;
import com.tuvarna.hotel.persistence.repositories.ReservationRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class ReservationRepositoryImpl extends BaseRepositoryImpl<ReservationEntity,UUID> implements ReservationRepository<ReservationEntity,UUID> {


    public ReservationRepositoryImpl() {
        super(ReservationEntity.class);
    }

    @Override
    public List<RoomEntity> getAllFreeRooms(HotelEntity hotel, Timestamp startDate, Timestamp endDate) {
        Session session = HibernateUtil.openSession();
        Transaction transaction = session.beginTransaction();
        List<RoomEntity> rooms = new LinkedList<>();
        try{
            String jpql = "SELECT r FROM Rooms r " +
                    "LEFT JOIN reservations rr ON (rr.room.id = r.id " +
                    "AND NOT ( " +
                    "(rr.startDate < '"+startDate+"' AND rr.endDate < '"+startDate+"') " +
                    "OR (rr.startDate > '"+endDate+"' AND rr.endDate > '"+endDate+"'))) " +
                    "WHERE rr.room.id IS NULL AND r.hotel.id = '"+hotel.getId()+"' AND r.number != '"+13+"'" +
                    "ORDER BY r.number ASC";

            rooms.addAll(session.createQuery(jpql, RoomEntity.class).getResultList());
            transaction.commit();

        } catch (Exception e) {

        } finally {
            session.close();
        }
        return rooms;
    }

    @Override
    public Long getLastReservationNumberOfHotel(HotelEntity hotel) {
        Session session = HibernateUtil.openSession();
        Transaction transaction = session.beginTransaction();
        Long lastReservationNumber = null;
        try{
            String jpql = "SELECT MAX (rr.number)  FROM Reservations rr WHERE rr.hotel.id = '"+hotel.getId()+"'";
            lastReservationNumber = (Long) session.createQuery(jpql).getSingleResult();
            transaction.commit();

        } catch (Exception e) {

        } finally {
            session.close();
        }
        return lastReservationNumber;
    }

    @Override
    public ReservationEntity getReservationWithNumber(Long reservationNumber, HotelEntity hotel) {
        Session session = HibernateUtil.openSession();
        Transaction transaction = session.beginTransaction();
        ReservationEntity reservation = null;
        try{
            String jpql = "SELECT rr FROM Reservations rr WHERE rr.number = '"+reservationNumber+"' AND rr.hotel = '"+ hotel.getId() +"' AND ROWNUM <=1 ORDER BY rr.room.number ";
            reservation = (ReservationEntity) session.createQuery(jpql).getSingleResult();
            transaction.commit();

        } catch (Exception e) {
        } finally {
            session.close();
        }
        return reservation;
    }
}
