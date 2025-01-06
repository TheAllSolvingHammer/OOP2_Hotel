package com.tuvarna.hotel.rest.controllers.reception.queries;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.entities.Hotel;
import com.tuvarna.hotel.api.models.entities.Reservation;
import com.tuvarna.hotel.api.models.get.receptionist.hotels.GetAllHotelsEmployeeInput;
import com.tuvarna.hotel.api.models.get.receptionist.hotels.GetAllHotelsEmployeeOutput;
import com.tuvarna.hotel.api.models.query.reservation.QueryReservationInput;
import com.tuvarna.hotel.api.models.query.reservation.QueryReservationOutput;
import com.tuvarna.hotel.core.instantiator.SessionManager;
import com.tuvarna.hotel.core.processes.GetAllHotelsEmployeeProcess;
import com.tuvarna.hotel.core.processes.QueryReservationsProcess;
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

public class QueryReservations implements Initializable {

    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private ComboBox<Hotel> hotels;
    private Boolean flag;
    private final GetAllHotelsEmployeeProcess displayHotelProcess;
    private final QueryReservationsProcess queryReservationsProcess;
    private ObservableList<Hotel> data;
    private List<Reservation> reservations;

    public QueryReservations() {
        flag = false;
        displayHotelProcess = SingletonManager.getInstance(GetAllHotelsEmployeeProcess.class);
        queryReservationsProcess = SingletonManager.getInstance(QueryReservationsProcess.class);
        reservations = new ArrayList<>();
    }

    public void switchToBeginning(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/reception/receptionist-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
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
        Either<ErrorProcessor, GetAllHotelsEmployeeOutput> result= displayHotelProcess.process(input);
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

    public void getQuery(ActionEvent event) throws IOException {
        getQueryResult();
        if(!flag){
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tuvarna/hotel/rest/reception/query-more-reservations.fxml"));
        Parent root = loader.load();
        QueryReservationsTable controller = loader.getController();
        controller.setReservationsList(reservations);
        controller.display();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Hotel Details");
        stage.show();
    }

    private void getQueryResult(){
        if(hotels.getValue()==null){
            return;
        }
        QueryReservationInput input = QueryReservationInput.builder()
                .startDate(startDate.getValue())
                .endDate(endDate.getValue())
                .hotelId(hotels.getValue().getId())
                .build();
        Either<ErrorProcessor, QueryReservationOutput> result=queryReservationsProcess.process(input);
        result.fold(
          error->{
              AlertManager.showAlert(Alert.AlertType.ERROR,"Error in receptionist reservation query", error.getMessage());
              return null;
          },
          success->{
              reservations =success.getReservations();
              flag=true;
              return null;
          }
        );
    }
}
