package com.tuvarna.hotel.rest.controllers.reception.queries;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.entities.Hotel;
import com.tuvarna.hotel.api.models.entities.RoomQueryDTO;
import com.tuvarna.hotel.api.models.get.receptionist.hotels.GetAllHotelsEmployeeInput;
import com.tuvarna.hotel.api.models.get.receptionist.hotels.GetAllHotelsEmployeeOutput;
import com.tuvarna.hotel.api.models.query.room.QueryRoomUsageInput;
import com.tuvarna.hotel.api.models.query.room.QueryRoomUsageOutput;
import com.tuvarna.hotel.core.instantiator.SessionManager;
import com.tuvarna.hotel.core.processes.GetAllHotelsEmployeeProcess;
import com.tuvarna.hotel.core.processes.QueryRoomUsageProcess;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class QueryRoomUsageReception implements Initializable {
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private ComboBox<Hotel> hotels;
    private Stage stage;
    private Parent root;
    private Scene scene;
    private Boolean flag;
    private final QueryRoomUsageProcess queryRoomUsageProcess;
    private final GetAllHotelsEmployeeProcess getAllHotelsProcess;
    private ObservableList<Hotel> data;
    private List<RoomQueryDTO> roomQueryDTOS;

    public QueryRoomUsageReception() {
        this.flag = false;
        queryRoomUsageProcess = SingletonManager.getInstance(QueryRoomUsageProcess.class);
        getAllHotelsProcess= SingletonManager.getInstance(GetAllHotelsEmployeeProcess.class);
        roomQueryDTOS = new ArrayList<>();
    }

    public void switchToBeginning(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/reception/receptionist-view.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Receptionist");
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        flag=false;
        GetAllHotelsEmployeeInput input = GetAllHotelsEmployeeInput.builder()
                .id(SessionManager.getInstance().getLoggedInUser().getId())
                .build();
        Either<ErrorProcessor, GetAllHotelsEmployeeOutput> result= getAllHotelsProcess.process(input);
        result.fold(
                error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in displaying hotels",error.getMessage());
                    return null;
                },
                success -> {
                    data = FXCollections.observableArrayList(success.getHotelList());
                    hotels.setItems(data);
                    hotels.getSelectionModel().select(0);
                    return null;
                });

    }

    private void getQueryResult(){
        if(hotels.getValue()==null){
            return;
        }
        QueryRoomUsageInput input = QueryRoomUsageInput.builder()
                .startDate(startDate.getValue())
                .endDate(endDate.getValue())
                .hotelId(hotels.getValue().getId())
                .build();
        Either<ErrorProcessor, QueryRoomUsageOutput> result= queryRoomUsageProcess.process(input);
        result.fold(
                error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in getting rooms",error.getMessage());
                    return null;
                },
                success -> {
                    roomQueryDTOS=success.getRoomsList();
                    flag=true;
                    return null;
                });
    }
    public void getQuery(ActionEvent event) throws IOException {
        getQueryResult();
        if(!flag){
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tuvarna/hotel/rest/reception/query-more-room-usage.fxml"));
        Parent root = loader.load();
        QueryRoomUsageReceptionTable controller= loader.getController();
        controller.setRoomQueryDTOS(roomQueryDTOS);
        controller.display();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Room usage details");
        stage.show();
    }
}
