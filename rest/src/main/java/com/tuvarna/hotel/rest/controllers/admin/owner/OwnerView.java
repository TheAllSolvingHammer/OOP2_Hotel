package com.tuvarna.hotel.rest.controllers.admin.owner;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.display.owner.DisplayOwnersInput;
import com.tuvarna.hotel.api.models.display.owner.DisplayOwnersOutput;
import com.tuvarna.hotel.api.models.display.owner.Owner;
import com.tuvarna.hotel.core.processes.DisplayOwnersProcess;
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
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class OwnerView implements Initializable{
    private Stage stage;
    private Parent root;
    private Scene scene;
    private final DisplayOwnersProcess displayOwnersProcess;
    @FXML
    private TableView<Owner> table;
    @FXML
    private TableColumn<Owner, UUID> id;
    @FXML
    private TableColumn<Owner, String> firstName;
    @FXML
    private TableColumn<Owner, String> lastName;
    @FXML
    private TableColumn<Owner, String> email;
    @FXML
    private TableColumn<Owner, String> phoneNumber;

    public OwnerView() {
        displayOwnersProcess=SingletonManager.getInstance(DisplayOwnersProcess.class);
    }


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

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        DisplayOwnersInput input = DisplayOwnersInput.builder().build();
        Either<ErrorProcessor, DisplayOwnersOutput> result= displayOwnersProcess.process(input);
        System.out.println(result);
        result.fold(
                error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in displaying owners",error.getMessage());
                    return null;
                },
                success -> {
                    ObservableList<Owner> data = FXCollections.observableArrayList(success.getOwnerList());
                    table.setItems(data);
                    table.refresh();
                    return null;
                }
        );
    }
}
