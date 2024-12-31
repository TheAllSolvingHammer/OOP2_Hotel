package com.tuvarna.hotel.rest.controllers.owner.hotel;

import com.tuvarna.hotel.api.models.entities.Hotel;
import com.tuvarna.hotel.api.models.entities.ReceptionistDTO;
import com.tuvarna.hotel.core.processes.QueryReceptionistProcess;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.dtos.ReceptionistReservationDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

public class QueryReceptionistTable  {
    @FXML
    private TableView<ReceptionistDTO> table;
    @FXML
    private TableColumn<ReceptionistDTO, UUID> reservation;
    @FXML
    private TableColumn<ReceptionistDTO,String> receptionist;
    private Stage stage;
    private Parent root;
    private Scene scene;
    @Setter
    private List<ReceptionistDTO> receptionistDTOList;
    public void handleBackButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void display(){
        reservation.setCellValueFactory(new PropertyValueFactory<>("reservationID"));
        receptionist.setCellValueFactory(new PropertyValueFactory<>("receptionistName"));
        ObservableList<ReceptionistDTO> data =FXCollections.observableArrayList(receptionistDTOList);
        table.setItems(data);
        table.refresh();
    }
}
