package com.tuvarna.hotel.rest.controllers.manager.hotel;

import com.tuvarna.hotel.api.enums.TypeRoom;
import com.tuvarna.hotel.api.models.entities.RoomQueryDTO;
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
import java.util.List;

public class QueryRoomUsageTable {
    @FXML
    private TextField searchBar;
    @FXML
    private TableView<RoomQueryDTO> table;
    @FXML
    private TableColumn<RoomQueryDTO,String> roomNumber;
    @FXML
    private TableColumn<RoomQueryDTO, BigDecimal> price;
    @FXML
    private TableColumn<RoomQueryDTO, TypeRoom> type;
    @FXML
    private TableColumn<RoomQueryDTO,Long> usage;
    @Setter
    private List<RoomQueryDTO> roomQueryDTOS;

    public void handleBackButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void display(){
        roomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        type.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        usage.setCellValueFactory(new PropertyValueFactory<>("usageCount"));
        ObservableList<RoomQueryDTO> data = FXCollections.observableArrayList(roomQueryDTOS);
        table.setItems(data);
        table.refresh();

        FilteredList<RoomQueryDTO> filteredData=new FilteredList<>(data, b->true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(receptionistDTO -> {
                if(newValue.isBlank() || newValue.isEmpty()){
                    return true;
                }

                String searchKeyword=newValue.toLowerCase();

                return receptionistDTO.getRoomNumber().toLowerCase().contains(searchKeyword);

            });
        });

        SortedList<RoomQueryDTO> sortedList=new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList);
    }

    public void clearTextField(ActionEvent event) {
        searchBar.clear();
    }
}
