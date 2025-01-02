package com.tuvarna.hotel.rest.alert;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import javafx.stage.StageStyle;
import lombok.Getter;

public final class AlertManager {
    @Getter
    private static final ObservableList<String> alertLog = FXCollections.observableArrayList();
    public static void showAlert(Alert.AlertType type, String header, String message){
        Alert alert = new Alert(type);
        alert.setHeaderText(header);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alertLog.add(formatAlertLog(header, message));
        alert.showAndWait();
    }

    private static String formatAlertLog(String header, String message) {
        return String.format("%s: %s", header, message);
    }

    public static void clearNotifications(){
        alertLog.clear();
    }

}
