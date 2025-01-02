package com.tuvarna.hotel.rest.controllers.owner.hotel;

import com.tuvarna.hotel.api.enums.TypeRoom;
import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.create.room.CreateRoomInput;
import com.tuvarna.hotel.api.models.create.room.CreateRoomOutput;
import com.tuvarna.hotel.api.models.entities.Hotel;
import com.tuvarna.hotel.api.models.entities.Room;
import com.tuvarna.hotel.api.models.get.rooms.GetAllRoomsPerHotelInput;
import com.tuvarna.hotel.api.models.get.rooms.GetAllRoomsPerHotelOutput;
import com.tuvarna.hotel.core.processes.CreateRoomProcess;
import com.tuvarna.hotel.core.processes.GetRoomsPerHotelProcess;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.rest.alert.AlertManager;
import io.vavr.control.Either;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HotelOwnerRoomDetails implements Initializable{
    private Stage stage;
    private Parent root;
    private Scene scene;
    @FXML
    private ListView<Room> listView;
    @FXML
    private TextField number;
    @FXML
    private TextField price;
    @FXML
    private TextField floor;
    @FXML
    private ComboBox<TypeRoom> typeCombo;
    @Setter
    private Hotel hotel;

    private List<Room> roomList;

    private final GetRoomsPerHotelProcess getAllRoomsProcess = SingletonManager.getInstance(GetRoomsPerHotelProcess.class);
    private final CreateRoomProcess createRoomProcess = SingletonManager.getInstance(CreateRoomProcess.class);

    public HotelOwnerRoomDetails() {
        roomList=new ArrayList<>();
    }

    public void handleBackButton(ActionEvent event) throws IOException {

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void addNewRoom(ActionEvent event) {
        Integer i;
        BigDecimal val;
        try{
            i=Integer.parseInt(floor.getText());
            val=BigDecimal.valueOf(Double.parseDouble(price.getText()));

        }
        catch (NumberFormatException exception){
            AlertManager.showAlert(Alert.AlertType.WARNING,"Number is not valid",exception.getMessage());
            return;
        }
        catch (NullPointerException exception){
            AlertManager.showAlert(Alert.AlertType.WARNING,"Empty price",exception.getMessage());
            return;
        }

        CreateRoomInput input = CreateRoomInput.builder()
                .roomNumber(number.getText())
                .floor(i)
                .type(typeCombo.getValue())
                .price(val)
                .hotelID(hotel.getId())
                .build();
        Either<ErrorProcessor, CreateRoomOutput> result= createRoomProcess.process(input);
        result.fold(
                error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in creating rooms",error.getMessage());
                    return null;
                },
                success -> {
                    AlertManager.showAlert(Alert.AlertType.CONFIRMATION,"Success",success.getMessage());
                    getAllRoomsForHotel();
                    return null;
                }
        );

    }

    public void display(){
        getAllRoomsForHotel();
    }

    private void getAllRoomsForHotel(){
        GetAllRoomsPerHotelInput input = GetAllRoomsPerHotelInput.builder()
                .hotelID(hotel.getId())
                .build();
        Either<ErrorProcessor, GetAllRoomsPerHotelOutput> result=getAllRoomsProcess.process(input);
        result.fold(
                error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in getting all rooms",error.getMessage());
                    return null;
                },
                success -> {
                    roomList = success.getRoomList();
                    ObservableList<Room> data = FXCollections.observableArrayList(roomList);
                    listView.setItems(data);
                    listView.refresh();
                    return null;
                }
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<TypeRoom> data = FXCollections.observableArrayList(TypeRoom.getMap().values());
        typeCombo.setItems(data);
        typeCombo.getSelectionModel().select(0);
    }
}
