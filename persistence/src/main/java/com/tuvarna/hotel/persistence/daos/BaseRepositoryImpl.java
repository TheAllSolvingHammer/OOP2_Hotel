package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.persistence.connection.HibernateUtil;
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

@RequiredArgsConstructor
public abstract class BaseRepositoryImpl<T extends EntityMarker,E extends UUID> implements BaseRepository<T,E> {

}
