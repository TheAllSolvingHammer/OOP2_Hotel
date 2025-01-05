package com.tuvarna.hotel.rest.controllers.manager.hotel;

import com.tuvarna.hotel.api.enums.StatusReservation;
import com.tuvarna.hotel.api.enums.TypeReservation;
import com.tuvarna.hotel.api.models.entities.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class QueryReservationsManagerTable {
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
    @FXML
    @Setter
    private List<Reservation> reservationsList;

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
        ObservableList<Reservation> data = FXCollections.observableArrayList(reservationsList);
        table.setItems(data);
        table.refresh();

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

    public void clearTextField(ActionEvent event) {
        searchBar.clear();
    }
}
