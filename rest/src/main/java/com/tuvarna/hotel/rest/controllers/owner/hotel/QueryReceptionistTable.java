package com.tuvarna.hotel.rest.controllers.owner.hotel;

import com.tuvarna.hotel.api.models.entities.ReceptionistDTO;
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

import java.util.List;
import java.util.UUID;

public class QueryReceptionistTable  {
    @FXML
    private TextField searchBar;
    @FXML
    private TableView<ReceptionistDTO> table;
    @FXML
    private TableColumn<ReceptionistDTO, UUID> reservation;
    @FXML
    private TableColumn<ReceptionistDTO,String> receptionist;
    @Setter
    private List<ReceptionistDTO> receptionistDTOList;
    private ObservableList<ReceptionistDTO> data;
    public void handleBackButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void display(){
        reservation.setCellValueFactory(new PropertyValueFactory<>("reservationID"));
        receptionist.setCellValueFactory(new PropertyValueFactory<>("receptionistName"));
        data =FXCollections.observableArrayList(receptionistDTOList);
        table.setItems(data);
        table.refresh();

        FilteredList<ReceptionistDTO> filteredData=new FilteredList<>(data, b->true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(receptionistDTO -> {
                if(newValue.isBlank() || newValue.isEmpty() || newValue==null){
                    return true;
                }

                String searchKeyword=newValue.toLowerCase();

                return receptionistDTO.getReceptionistName().toLowerCase().contains(searchKeyword);

            });
        });

        SortedList<ReceptionistDTO> sortedList=new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList);
    }

    public void clearTextField(ActionEvent event) {
        searchBar.clear();
    }
}
