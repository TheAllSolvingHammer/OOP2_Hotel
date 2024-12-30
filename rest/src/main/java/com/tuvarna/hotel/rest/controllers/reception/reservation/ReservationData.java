package com.tuvarna.hotel.rest.controllers.reception.reservation;

import com.tuvarna.hotel.api.enums.TypeReservation;
import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.create.reservation.CreateReservationInput;
import com.tuvarna.hotel.api.models.create.reservation.CreateReservationOutput;
import com.tuvarna.hotel.api.models.display.service.DisplayServicesInput;
import com.tuvarna.hotel.api.models.display.service.DisplayServicesOutput;
import com.tuvarna.hotel.api.models.entities.Client;
import com.tuvarna.hotel.api.models.entities.Hotel;
import com.tuvarna.hotel.api.models.entities.Room;
import com.tuvarna.hotel.api.models.entities.Service;
import com.tuvarna.hotel.api.models.get.rooms.GetAllRoomsPerHotelInput;
import com.tuvarna.hotel.api.models.get.rooms.GetAllRoomsPerHotelOutput;
import com.tuvarna.hotel.api.models.query.client.information.ClientInformationInput;
import com.tuvarna.hotel.api.models.query.client.information.ClientInformationOutput;
import com.tuvarna.hotel.core.processes.ClientInformationProcess;
import com.tuvarna.hotel.core.processes.CreateReservationProcess;
import com.tuvarna.hotel.core.processes.DisplayServicesProcess;
import com.tuvarna.hotel.core.processes.GetRoomsPerHotelProcess;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.rest.alert.AlertManager;
import io.vavr.control.Either;
import javafx.beans.Observable;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Setter;
import org.controlsfx.control.CheckComboBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ReservationData implements Initializable {
    @FXML
    private VBox serviceVbox;
    @FXML
    private ComboBox<TypeReservation> typeOfReservation;
    @FXML
    private ComboBox<Client> clients;
    @FXML
    private ComboBox<Room> rooms;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    private Stage stage;
    private Parent root;
    private Scene scene;
    @Setter
    private Hotel hotel;
    private CheckComboBox<Service> checkComboBox;
    private final ClientInformationProcess clientInformationProcess = SingletonManager.getInstance(ClientInformationProcess.class);
    private final GetRoomsPerHotelProcess getRoomsPerHotelProcess = SingletonManager.getInstance(GetRoomsPerHotelProcess.class);
    private final CreateReservationProcess createReservationProcess = SingletonManager.getInstance(CreateReservationProcess.class);

    public ReservationData() {
        checkComboBox=new CheckComboBox<>();
    }

    @FXML
    private void switchToTable(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void display(){
        getAllClients();
        getRooms();
        displayServices();
    }

    private void getAllClients(){
        ClientInformationInput input = ClientInformationInput.builder().build();
        Either<ErrorProcessor, ClientInformationOutput> result=clientInformationProcess.process(input);
        result.fold(
          error-> {
              AlertManager.showAlert(Alert.AlertType.ERROR, "Error in displaying clients", error.getMessage());
              return null;
          },
          success->{
              ObservableList<Client> data = FXCollections.observableArrayList(success.getClientList());
              clients.setItems(data);
              clients.getSelectionModel().select(0);
              return null;
          }
        );
    }

    private void getRooms(){
        GetAllRoomsPerHotelInput input = GetAllRoomsPerHotelInput.builder()
                .hotelID(hotel.getId())
                .build();
        Either<ErrorProcessor, GetAllRoomsPerHotelOutput> result=getRoomsPerHotelProcess.process(input);
        result.fold(
                error-> {
                    AlertManager.showAlert(Alert.AlertType.ERROR, "Error in displaying rooms", error.getMessage());
                    return null;
                },
                success->{
                    ObservableList<Room> data = FXCollections.observableArrayList(success.getRoomList());
                    rooms.setItems(data);
                    rooms.getSelectionModel().select(0);
                    return null;
                }
        );
    }

    private void displayServices() {
        checkComboBox.getItems().addAll(hotel.getServiceList());
        serviceVbox.getChildren().add(checkComboBox);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<TypeReservation> data = FXCollections.observableArrayList(TypeReservation.getMap().values());
        typeOfReservation.setItems(data);
        typeOfReservation.getSelectionModel().select(0);
    }

    public void createReservation(ActionEvent event) {

        CreateReservationInput input = CreateReservationInput.builder()
                .room(rooms.getValue())
                .client(clients.getValue())
                .services(checkComboBox.getCheckModel().getCheckedItems())
                .type(typeOfReservation.getValue())
                .startDate(startDate.getValue())
                .endDate(endDate.getValue())
                .build();
        Either<ErrorProcessor, CreateReservationOutput> result = createReservationProcess.process(input);
        result.fold(
                error-> {
                    AlertManager.showAlert(Alert.AlertType.ERROR, "Error in creating reservation", error.getMessage());
                    return null;
                },
                success->{
                    AlertManager.showAlert(Alert.AlertType.CONFIRMATION,"Success",success.getMessage());
                    return null;
                }
        );

    }
}