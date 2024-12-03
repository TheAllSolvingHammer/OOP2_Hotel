package com.tuvarna.hotel.rest;

import com.tuvarna.hotel.core.aspect.LogExecution;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
@LogExecution
public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField textField;
    @FXML
    private Button myButton;

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



}