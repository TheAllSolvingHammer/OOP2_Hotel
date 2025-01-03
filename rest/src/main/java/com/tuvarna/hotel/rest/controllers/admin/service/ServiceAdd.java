package com.tuvarna.hotel.rest.controllers.admin.service;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.create.service.CreateServiceInput;
import com.tuvarna.hotel.api.models.create.service.CreateServiceOutput;
import com.tuvarna.hotel.core.processes.CreateServiceProcess;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.rest.alert.AlertManager;
import io.vavr.control.Either;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;

public class ServiceAdd {

    private Stage stage;
    private Parent root;
    private Scene scene;
    @FXML
    private TextField name;
    @FXML
    private TextField price;

    private final CreateServiceProcess createServiceProcess;

    public ServiceAdd() {
        createServiceProcess = SingletonManager.getInstance(CreateServiceProcess.class);
    }

    public void switchToBeginning(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/admin/services-scene.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Services");
        stage.show();
    }


    public void createService(ActionEvent event) {
        BigDecimal val;
        try{
            val=BigDecimal.valueOf(Double.parseDouble(price.getText()));
        }
        catch (Exception exception){
            AlertManager.showAlert(Alert.AlertType.WARNING,"Error in parsing price",exception.getMessage());
            return;
        }
        CreateServiceInput input = CreateServiceInput.builder()
                .serviceName(name.getText())
                .price(val)
                .build();
        Either<ErrorProcessor, CreateServiceOutput> result = createServiceProcess.process(input);
        result.fold(error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in creating service",error.getMessage());
                    return null;
                },
                success -> {
                    AlertManager.showAlert(Alert.AlertType.CONFIRMATION,"Success",success.getMessage());
                    clearTextFields();
                    return null;
                });
    }

    public void clearTextFields(){
        name.clear();
        price.clear();
    }
}
