package com.tuvarna.hotel.rest.controllers.admin.manager;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.display.manager.DisplayManagerInput;
import com.tuvarna.hotel.api.models.display.manager.DisplayManagerOperation;
import com.tuvarna.hotel.api.models.display.manager.DisplayManagerOutput;
import com.tuvarna.hotel.api.models.display.manager.Manager;
import com.tuvarna.hotel.api.models.display.owner.DisplayOwnersOutput;
import com.tuvarna.hotel.api.models.display.owner.Owner;
import com.tuvarna.hotel.core.processes.DisplayManagersProcess;
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
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

public class ManagerView implements Initializable {
    private Stage stage;
    private Parent root;
    private Scene scene;
    private final DisplayManagersProcess displayManagerProcess;
    @FXML
    private TableView<Manager> table;
    @FXML
    private TableColumn<Manager, UUID> id;
    @FXML
    private TableColumn<Manager, String> firstName;
    @FXML
    private TableColumn<Manager, String> lastName;
    @FXML
    private TableColumn<Manager, String> email;
    @FXML
    private TableColumn<Manager, String> phoneNumber;


    public ManagerView() {
        this.displayManagerProcess= SingletonManager.getInstance(DisplayManagersProcess.class);
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
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        DisplayManagerInput input = DisplayManagerInput.builder().build();
        Either<ErrorProcessor, DisplayManagerOutput> result= displayManagerProcess.process(input);
        result.fold(
                error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in displaying managers",error.getMessage());
                    return null;
                },
                success -> {
                    ObservableList<Manager> data = FXCollections.observableArrayList(success.getManagerList());
                    table.setItems(data);
                    table.refresh();
                    return null;
                }
        );

    }
}
