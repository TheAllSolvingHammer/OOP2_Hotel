package com.tuvarna.hotel.rest.controllers.owner.hotel;

import com.tuvarna.hotel.api.models.entities.Hotel;
import com.tuvarna.hotel.api.models.entities.ServicesDTO;
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

public class QueryHotelAndServiceTable {
    @FXML
    private TextField searchBar;
    @FXML
    private TableView<ServicesDTO> table;
    @FXML
    private TableColumn<ServicesDTO,String> service;
    @FXML
    private TableColumn<ServicesDTO,Long> amount;
    @FXML
    private TableColumn<ServicesDTO, BigDecimal> revenue;
    @Setter
    private List<ServicesDTO> servicesDTOS;
    private ObservableList<ServicesDTO> data;

    public void handleBackButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    public void display(){
        service.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        amount.setCellValueFactory(new PropertyValueFactory<>("usageCount"));
        revenue.setCellValueFactory(new PropertyValueFactory<>("totalRevenue"));
        data = FXCollections.observableArrayList(servicesDTOS);
        table.setItems(data);
        table.refresh();


        FilteredList<ServicesDTO> filteredData=new FilteredList<>(data, b->true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(servicesDTO -> {
                if(newValue.isBlank() || newValue.isEmpty() || newValue==null){
                    return true;
                }

                String searchKeyword=newValue.toLowerCase();

                if(servicesDTO.getServiceName().toLowerCase().contains(searchKeyword)){
                    return true;
                }
                else if(servicesDTO.getTotalRevenue().toString().toLowerCase().contains(searchKeyword)){
                    return true;
                }
                else return servicesDTO.getUsageCount().toString().toLowerCase().contains(searchKeyword);
            });
        });

        SortedList<ServicesDTO> sortedList=new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList);
    }

    public void clearTextField(ActionEvent event) {
        searchBar.clear();
    }
}
