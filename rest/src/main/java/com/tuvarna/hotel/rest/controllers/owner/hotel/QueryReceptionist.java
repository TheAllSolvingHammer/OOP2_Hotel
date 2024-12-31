package com.tuvarna.hotel.rest.controllers.owner.hotel;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.display.hotel.DisplayHotelsInput;
import com.tuvarna.hotel.api.models.display.hotel.DisplayHotelsOutput;
import com.tuvarna.hotel.api.models.entities.Hotel;
import com.tuvarna.hotel.api.models.entities.ReceptionistDTO;
import com.tuvarna.hotel.api.models.query.receptionist.information.QueryReceptionistInput;
import com.tuvarna.hotel.api.models.query.receptionist.information.QueryReceptionistOutput;
import com.tuvarna.hotel.core.instantiator.SessionManager;
import com.tuvarna.hotel.core.processes.DisplayOwnerHotelProcess;
import com.tuvarna.hotel.core.processes.QueryReceptionistProcess;
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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class QueryReceptionist implements Initializable {

    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private ComboBox<Hotel> hotels;
    private Stage stage;
    private Parent root;
    private Scene scene;

    private final DisplayOwnerHotelProcess displayHotelProcess = SingletonManager.getInstance(DisplayOwnerHotelProcess.class);
    private final QueryReceptionistProcess queryReceptionistProcess = SingletonManager.getInstance(QueryReceptionistProcess.class);
    private ObservableList<Hotel> data;
    private List<ReceptionistDTO> receptionistDTOS= new ArrayList<>();

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

    public void getQuery(ActionEvent event) throws IOException {
        getQueryResult();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tuvarna/hotel/rest/owner/query-more-receptionist.fxml"));
        Parent root = loader.load();
        QueryReceptionistTable controller = loader.getController();
        controller.setReceptionistDTOList(receptionistDTOS);
        controller.display();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Hotel Details");
        stage.show();
    }

    private void getQueryResult(){
        QueryReceptionistInput input = QueryReceptionistInput.builder()
                .startDate(startDate.getValue())
                .endDate(endDate.getValue())
                .hotelId(hotels.getValue().getId())
                .build();
        Either<ErrorProcessor, QueryReceptionistOutput> result=queryReceptionistProcess.process(input);
        result.fold(
          error->{
              AlertManager.showAlert(Alert.AlertType.ERROR,"Error in receptionist reservation query", error.getMessage());
              return null;
          },
          success->{
              receptionistDTOS=success.getReceptionistDTOS();
              return null;
          }
        );
    }
}
