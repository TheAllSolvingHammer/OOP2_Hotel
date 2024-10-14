module com.tuvarna.hotel.persistence {
    exports com.tuvarna.hotel.persistence.entities;
    exports com.tuvarna.hotel.persistence.repositories;
    exports com.tuvarna.hotel.persistence.daos;
    requires lombok;
    requires jakarta.persistence;
    requires com.fasterxml.jackson.databind;
    requires org.hibernate.orm.core;

}