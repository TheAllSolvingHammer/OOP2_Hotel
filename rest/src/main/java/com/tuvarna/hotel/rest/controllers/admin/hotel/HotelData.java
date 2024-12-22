package com.tuvarna.hotel.rest.controllers.admin.hotel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class HotelData implements Initializable {
    private Stage stage;
    private Parent root;
    private Scene scene;

    @FXML
    private TextField newService;
    @FXML
    private ListView<String> myListView;

    String[] something={"sauna","pool","else"};
    String currentService;


    @FXML
    protected void switchToBeginning(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/admin/hotels-scene.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Admin");
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        myListView.getItems().addAll(something);

        myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                currentService=myListView.getSelectionModel().getSelectedItem();


            }
        });
    }

    @FXML
    public void addService(ActionEvent event) throws IOException {
        myListView.getItems().add(newService.getText());
    }
}
