package com.tuvarna.hotel.rest.controllers.admin.hotel;


import com.tuvarna.hotel.api.models.entities.Hotel;
import com.tuvarna.hotel.api.models.entities.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;


public class HotelData {
    private Stage stage;
    private Parent root;
    private Scene scene;
    @Setter
    private Hotel hotel;
    @FXML
    private TextField newService;
    @FXML
    private ListView<String> myListView;
    @FXML
    private Label name;
    @FXML
    private Label locationHotel;
    @FXML
    private Label stars;

    @FXML
    protected void switchToBeginning(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }



    public void display() {
        myListView.getItems().addAll(hotel.getServiceList().stream().map(Service::getName).toList());
        name.setText(hotel.getName());
        locationHotel.setText(hotel.getLocation());
        stars.setText(hotel.getStars().toString());
//        myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
//
//            @Override
//            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
//                currentService=myListView.getSelectionModel().getSelectedItem();
//
//
//            }
//        });
    }

    @FXML
    public void addService(ActionEvent event) throws IOException {
        myListView.getItems().add(newService.getText());
    }


}
