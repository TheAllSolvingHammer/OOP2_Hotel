package com.tuvarna.hotel.rest.controllers.reception.reservation;

import com.tuvarna.hotel.api.enums.StatusReservation;
import com.tuvarna.hotel.api.enums.TypeReservation;
import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.display.reservation.DisplayAllReservationsHotelInput;
import com.tuvarna.hotel.api.models.display.reservation.DisplayAllReservationsHotelOutput;
import com.tuvarna.hotel.api.models.entities.Hotel;
import com.tuvarna.hotel.api.models.entities.Reservation;
import com.tuvarna.hotel.core.processes.DisplayAllReservationsHotelProcess;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.rest.alert.AlertManager;
import io.vavr.control.Either;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

public class DisplayReservationsHotel {
    @Setter
    private Hotel hotel;
    @FXML
    private TableView<Reservation> table;
    @FXML
    private TableColumn<Reservation,String> reservationNumber;
    @FXML
    private TableColumn<Reservation, BigDecimal> price;
    @FXML
    private TableColumn<Reservation, TypeReservation> type;
    @FXML
    private TableColumn<Reservation, LocalDate> startDate;
    @FXML
    private TableColumn<Reservation,LocalDate> endDate;
    @FXML
    private TableColumn<Reservation,String> room;
    @FXML
    private TableColumn<Reservation,String> client;
    @FXML
    private TableColumn<Reservation, StatusReservation> status;
    @FXML
    private TextField searchBar;
    private ObservableList<Reservation> data;
    private final DisplayAllReservationsHotelProcess displayAllReservationsHotelProcess;
    public DisplayReservationsHotel() {
        displayAllReservationsHotelProcess= SingletonManager.getInstance(DisplayAllReservationsHotelProcess.class);
    }

    public void handleBackButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void display(){
        reservationNumber.setCellValueFactory(new PropertyValueFactory<>("id"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        room.setCellValueFactory(new PropertyValueFactory<>("room"));
        client.setCellValueFactory(new PropertyValueFactory<>("client"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        getQuery();
        FilteredList<Reservation> filteredData=new FilteredList<>(data, b->true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(reservation -> {
            if(newValue.isBlank() || newValue.isEmpty()){
                return true;
            }

            String searchKeyword=newValue.toLowerCase();

            return reservation.getRoom().toLowerCase().contains(searchKeyword);

        }));

        SortedList<Reservation> sortedList=new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList);
    }

    private void getQuery(){
        DisplayAllReservationsHotelInput input = DisplayAllReservationsHotelInput.builder()
                .hotelID(hotel.getId())
                .build();
        Either<ErrorProcessor, DisplayAllReservationsHotelOutput> result= displayAllReservationsHotelProcess.process(input);
        result.fold(
                error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in displaying reservations",error.getMessage());
                    return null;
                },
                success -> {
                    data = FXCollections.observableArrayList(success.getReservations());
                    table.setItems(data);
                    table.refresh();
                    return null;
                });

    }

    public void clearTextField(ActionEvent event) {
        searchBar.clear();
    }

    public void getMoreReservationData(MouseEvent mouseEvent) throws IOException {
        if(mouseEvent.getClickCount()==2) {
            Reservation reservation = table.getSelectionModel().getSelectedItem();
            if(reservation==null ) return;
            showMoreReservationData(reservation);
        }
    }

    private void showMoreReservationData(Reservation reservation) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tuvarna/hotel/rest/reception/more-reservation-data.fxml"));
        Parent root = loader.load();
        DisplaySingleReservation controller = loader.getController();
        controller.setReservation(reservation);
        controller.display();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Reservation info");
        stage.setOnCloseRequest(windowEvent -> display());
        stage.setOnHidden(windowEvent -> display());
        stage.show();
    }
}
