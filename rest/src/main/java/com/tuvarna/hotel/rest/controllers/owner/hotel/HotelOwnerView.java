package com.tuvarna.hotel.rest.controllers.owner.hotel;

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
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HotelOwnerView implements Initializable {
    private Stage stage;
    private Parent root;
    private Scene scene;
    private final DisplayHotelProcess displayHotelProcess = SingletonManager.getInstance(DisplayHotelProcess.class);
    @FXML
    private TableView<Hotel> table;
    @FXML
    private TableColumn<Hotel, String> name;
    @FXML
    private TableColumn<Hotel, String> location;
    @FXML
    private TableColumn<Hotel, Integer> stars;

    @FXML
    protected void switchToBeginning(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/owner/owner-view.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Owner");
        stage.show();
    }

    @FXML
    public void displayHotel(MouseEvent mouseEvent) throws IOException {
        if(mouseEvent.getClickCount()==2) {
            Hotel hotel = table.getSelectionModel().getSelectedItem();
            showMoreHotelData(hotel);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        stars.setCellValueFactory(new PropertyValueFactory<>("stars"));
        DisplayHotelsInput hotelsInput = DisplayHotelsInput.builder()
                .build();
        Either<ErrorProcessor, DisplayHotelsOutput> result= displayHotelProcess.process(hotelsInput);
        result.fold(
                error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in displaying hotels",error.getMessage());
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

    public void showMoreHotelData(Hotel hotel) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tuvarna/hotel/rest/owner/more-hotel-owner-info.fxml"));
        Parent root = loader.load();
        HotelOwnerData controller = loader.getController();
        controller.setHotel(hotel);
        controller.display();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Hotel Data");
        stage.show();
    }

    public void addNewHotel(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/owner/add-hotel.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Owner");
        stage.show();
    }
}
