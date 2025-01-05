package com.tuvarna.hotel.persistence.connection;

import lombok.Getter;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final Logger log = Logger.getLogger(HibernateUtil.class);
    @Getter
    private static SessionFactory sessionFactory;
    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            log.error("Hibernate error encountered: "+e.getMessage());
            System.exit(0);
        }
    }

    public static Session openSession(){
        return sessionFactory.openSession();

    }
    public static void closeSession(){
        sessionFactory.close();
    }

}
