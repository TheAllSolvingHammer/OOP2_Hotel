package com.tuvarna.hotel.rest.controllers.admin.hotel;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.display.hotel.DisplayHotelsInput;
import com.tuvarna.hotel.api.models.display.hotel.DisplayHotelsOutput;
import com.tuvarna.hotel.api.models.display.hotel.Hotel;
import com.tuvarna.hotel.core.processes.DisplayHotelProcess;
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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HotelView implements Initializable {
    private Stage stage;
    private Parent root;
    private Scene scene;
    private final DisplayHotelProcess displayHotelProcess;
    @FXML
    private TableView<Hotel> table;
    @FXML
    private TableColumn<Hotel,Integer> id;
    @FXML
    private TableColumn<Hotel, String> name;
    @FXML
    private TableColumn<Hotel, String> location;
    @FXML
    private TableColumn<Hotel, Integer> stars;
    @FXML
    private TableColumn<Hotel, List<String>> serviceList;


    public HotelView() {
        displayHotelProcess= SingletonManager.getInstance(DisplayHotelProcess.class);
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
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        stars.setCellValueFactory(new PropertyValueFactory<>("stars"));
        serviceList.setCellValueFactory(new PropertyValueFactory<>("serviceList"));
        DisplayHotelsInput hotelsInput = DisplayHotelsInput.builder()
                .build();
        Either<ErrorProcessor, DisplayHotelsOutput> result= displayHotelProcess.process(hotelsInput);
        result.fold(
                error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in displaying owners",error.getMessage());
                    return null;
                },
                success -> {
                    ObservableList<Hotel> data = FXCollections.observableArrayList(success.getHotelList());
                    table.setItems(data);
                    table.refresh();
                    return null;
                }
        );
    }
    @FXML
    public void handleRowSelect(MouseEvent mouseEvent) {
        Hotel hotel = table.getSelectionModel().getSelectedItem();
        System.out.println(hotel);
    }
}
