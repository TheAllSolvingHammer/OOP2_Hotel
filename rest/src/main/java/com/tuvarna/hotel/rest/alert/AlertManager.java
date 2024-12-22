package com.tuvarna.hotel.rest.alert;

import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import javafx.stage.StageStyle;

public final class AlertManager {
    public static void showAlert(Alert.AlertType type, String header, String message){
        Alert alert = new Alert(type);
        alert.setHeaderText(header);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
}
