package com.tuvarna.hotel.rest.controllers.owner.hotel;

import com.tuvarna.hotel.api.models.entities.ServicesDTO;
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

import java.math.BigDecimal;
import java.util.List;

public class QueryHotelAndServiceTable {
    @FXML
    private TableView<ServicesDTO> table;
    @FXML
    private TableColumn<ServicesDTO,String> service;
    @FXML
    private TableColumn<ServicesDTO,Long> amount;
    @FXML
    private TableColumn<ServicesDTO, BigDecimal> revenue;
    private Stage stage;
    private Parent root;
    private Scene scene;
    @Setter
    private List<ServicesDTO> servicesDTOS;

    public void handleBackButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    public void display(){
        service.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        amount.setCellValueFactory(new PropertyValueFactory<>("usageCount"));
        revenue.setCellValueFactory(new PropertyValueFactory<>("totalRevenue"));
        ObservableList<ServicesDTO> data = FXCollections.observableArrayList(servicesDTOS);
        table.setItems(data);
        table.refresh();
    }
}
