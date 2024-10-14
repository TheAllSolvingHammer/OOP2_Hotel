module com.tuvarna.hotel {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires org.hibernate.orm.core;
    requires com.tuvarna.hotel.persistence;


    opens com.tuvarna.hotel.rest to javafx.fxml;
    exports com.tuvarna.hotel.rest;

}