package com.tuvarna.hotel.rest;

import javafx.event.ActionEvent;
import com.tuvarna.hotel.core.aspect.LogExecution;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField textField;
    @FXML
    private Button myButton;

    private Stage stage;
    private Parent root;
    private Scene scene;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onImageButtonClick() {
        if (!imageView.isVisible()) {

            if (imageView.getImage() == null) {
                InputStream imageStream = getClass().getResourceAsStream("/shrek.jpg");
                if (imageStream == null) {
                    System.out.println("Error: Image file not found.");
                } else {
                    Image myImage = new Image(imageStream);
                    imageView.setImage(myImage);
                }
            }
            imageView.setVisible(true);
        }

    }

    @FXML
   protected void TextBoxButtonClick() {

        textField.setVisible(true);

        textField.setText("mnogo si qk be!");


    }

    @FXML
    protected void switchToAdmin(ActionEvent event) throws IOException {
        root= FXMLLoader.load(getClass().getResource("admin-view.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Admin");
        stage.show();
    }

    @FXML
    protected void switchToUser(ActionEvent event) throws IOException {
        root= FXMLLoader.load(getClass().getResource("user-view.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("User");
        stage.show();
    }

    @FXML
    protected void switchToReservation(ActionEvent event) throws IOException {
        root= FXMLLoader.load(getClass().getResource("reservation-view.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Reservation System");
        stage.show();
    }

    @FXML
    protected void switchToOwner(ActionEvent event) throws IOException {
        root= FXMLLoader.load(getClass().getResource("owner-view.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Owner Data");
        stage.show();
    }

}