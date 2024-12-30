package com.tuvarna.hotel.rest.controllers.manager.receptionists;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.entities.Hotel;
import com.tuvarna.hotel.api.models.display.manager.hotel.DisplayManagerHotelInput;
import com.tuvarna.hotel.api.models.display.manager.hotel.DisplayManagerHotelOutput;
import com.tuvarna.hotel.core.instantiator.SessionManager;
import com.tuvarna.hotel.core.processes.DisplayManagerHotelProcess;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.rest.alert.AlertManager;
import com.tuvarna.hotel.rest.controllers.owner.hotel.HotelOwnerData;
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

public class ManagerHotelView implements Initializable {

    private Stage stage;
    private Parent root;
    private Scene scene;

    @FXML
    private TableView<Hotel> table;
    @FXML
    private TableColumn<Hotel, String> name;
    @FXML
    private TableColumn<Hotel, String> location;
    @FXML
    private TableColumn<Hotel,Integer> stars;

    private final DisplayManagerHotelProcess displayManagerHotelProcess = SingletonManager.getInstance(DisplayManagerHotelProcess.class);

    @FXML
    protected void switchToBeginning(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/manager/manager-view.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Manager");
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        stars.setCellValueFactory(new PropertyValueFactory<>("stars"));
        display();

    }
    private void display(){
        DisplayManagerHotelInput input= DisplayManagerHotelInput.builder()
                .id(SessionManager.getInstance().getLoggedInUser().getId())
                .build();

        Either<ErrorProcessor, DisplayManagerHotelOutput> result = displayManagerHotelProcess.process(input);
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
    public void openSpecificHotel(MouseEvent mouseEvent) throws IOException {
        if(mouseEvent.getClickCount()==2) {
            Hotel hotel = table.getSelectionModel().getSelectedItem();
            if(hotel==null) return;
            showMoreHotelData(hotel);
        }
        display();

    }

    public void showMoreHotelData(Hotel hotel) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tuvarna/hotel/rest/manager/more-hotel-manager-info.fxml"));
        Parent root = loader.load();
        ManagerHotelDetails controller = loader.getController();
        controller.setHotel(hotel);
        controller.display();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Hotel Information");
        stage.show();
    }
}
