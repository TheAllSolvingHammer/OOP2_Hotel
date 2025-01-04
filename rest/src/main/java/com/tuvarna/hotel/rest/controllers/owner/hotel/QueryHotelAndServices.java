package com.tuvarna.hotel.rest.controllers.owner.hotel;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.display.hotel.DisplayHotelsInput;
import com.tuvarna.hotel.api.models.display.hotel.DisplayHotelsOutput;
import com.tuvarna.hotel.api.models.entities.Hotel;
import com.tuvarna.hotel.api.models.entities.ServicesDTO;
import com.tuvarna.hotel.api.models.query.service.QueryServicesInput;
import com.tuvarna.hotel.api.models.query.service.QueryServicesOutput;
import com.tuvarna.hotel.core.instantiator.SessionManager;
import com.tuvarna.hotel.core.processes.DisplayOwnerHotelProcess;
import com.tuvarna.hotel.core.processes.QueryServicesProcess;
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

public class QueryHotelAndServices implements Initializable {
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
    private final DisplayOwnerHotelProcess displayHotelProcess;
    private final QueryServicesProcess queryServicesProcess;
    private ObservableList<Hotel> data;

    private List<ServicesDTO> serviceUsageDTOS= new ArrayList<>();

    public QueryHotelAndServices() {
        flag = false;
        displayHotelProcess = SingletonManager.getInstance(DisplayOwnerHotelProcess.class);
        queryServicesProcess = SingletonManager.getInstance(QueryServicesProcess.class);
    }

    public void getQuery(ActionEvent event) throws IOException {
        getQueryResult();
        if(!flag){
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tuvarna/hotel/rest/owner/query-more-service.fxml"));
        Parent root = loader.load();
        QueryHotelAndServiceTable controller = loader.getController();
        controller.setServicesDTOS(serviceUsageDTOS);
        controller.display();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Hotel Details");
        stage.show();
    }

    public void switchToBeginning(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/owner/owner-view.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Owner");
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        flag=false;
        DisplayHotelsInput input = DisplayHotelsInput.builder()
                .id(SessionManager.getInstance().getLoggedInUser().getId())
                .build();
        Either<ErrorProcessor, DisplayHotelsOutput> result= displayHotelProcess.process(input);
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
        QueryServicesInput input = QueryServicesInput.builder()
                .startDate(startDate.getValue())
                .endDate(endDate.getValue())
                .hotelId(hotels.getValue().getId())
                .build();
        Either<ErrorProcessor, QueryServicesOutput> result=queryServicesProcess.process(input);
        result.fold(
                error->{
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in services per hotel query", error.getMessage());
                    return null;
                },
                success->{
                    serviceUsageDTOS=success.getServicesDTOList();
                    flag=true;
                    return null;
                }
        );
    }
}
