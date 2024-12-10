package com.tuvarna.hotel.persistence.connection;


import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {



    @Getter
    private static SessionFactory sessionFactory;
    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();//creates SessionFactory
        } catch (Throwable e) {
            //todo
            System.out.println(e.getMessage());
        }
    }

    public static Session openSession(){
        return sessionFactory.openSession();

    }
    public static void closeSession(){
        sessionFactory.close();
    }
    public static void createSessionFactory(){}

}
