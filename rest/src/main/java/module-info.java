module com.tuvarna.hotel {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens com.tuvarna.hotel.rest to javafx.fxml;
    exports com.tuvarna.hotel.rest;
}