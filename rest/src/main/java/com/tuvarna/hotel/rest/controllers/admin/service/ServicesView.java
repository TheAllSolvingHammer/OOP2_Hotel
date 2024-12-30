package com.tuvarna.hotel.rest.controllers.admin.service;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.display.service.DisplayServicesInput;
import com.tuvarna.hotel.api.models.display.service.DisplayServicesOutput;
import com.tuvarna.hotel.api.models.entities.Service;
import com.tuvarna.hotel.core.processes.DisplayServicesProcess;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.rest.alert.AlertManager;
import io.vavr.control.Either;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class ServicesView implements Initializable {
    private Stage stage;
    private Parent root;
    private Scene scene;

    private final DisplayServicesProcess displayServicesProcess = SingletonManager.getInstance(DisplayServicesProcess.class);
    @FXML
    private TableView<Service> table;
    @FXML
    private TableColumn<Service,String> name;
    @FXML
    private TableColumn<Service, BigDecimal> price;


    @FXML
    protected void switchToBeginning(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/admin/admin-view.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Admin");
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        DisplayServicesInput input = DisplayServicesInput.builder()
                .build();
        Either<ErrorProcessor, DisplayServicesOutput> result = displayServicesProcess.process(input);
        result.fold(error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in displaying services",error.getMessage());
                    return null;
                },
                success -> {
                    ObservableList<Service> data = FXCollections.observableArrayList(success.getServiceList());
                    table.setItems(data);
                    table.refresh();
                    return null;
                });
    }

    public void createNewService(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/admin/service-add.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Service add");
        stage.show();
    }
}
