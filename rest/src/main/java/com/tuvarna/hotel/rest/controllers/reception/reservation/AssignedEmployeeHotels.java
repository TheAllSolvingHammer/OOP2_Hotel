package com.tuvarna.hotel.rest.controllers.reception.reservation;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.entities.Hotel;
import com.tuvarna.hotel.api.models.get.receptionist.hotels.GetAllHotelsEmployeeInput;
import com.tuvarna.hotel.api.models.get.receptionist.hotels.GetAllHotelsEmployeeOutput;
import com.tuvarna.hotel.core.instantiator.SessionManager;
import com.tuvarna.hotel.core.processes.GetAllHotelsEmployeeProcess;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.rest.alert.AlertManager;
import com.tuvarna.hotel.rest.controllers.manager.receptionists.ManagerHotelDetails;
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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AssignedEmployeeHotels implements Initializable {
    private final GetAllHotelsEmployeeProcess getAllHotelsProcess = SingletonManager.getInstance(GetAllHotelsEmployeeProcess.class);

    @FXML
    private TextField searchBar;
    @FXML
    private TableView<Hotel> table;
    @FXML
    private TableColumn<Hotel,String> name;
    @FXML
    private TableColumn<Hotel, String> location;
    @FXML
    private TableColumn<Hotel,Integer> stars;
    private Stage stage;
    private Parent root;
    private Scene scene;

    private ObservableList<Hotel> data;

    @FXML
    protected void switchToBeginning(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/reception/receptionist-view.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void clearTextField(ActionEvent actionEvent) {
        searchBar.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        stars.setCellValueFactory(new PropertyValueFactory<>("stars"));
        display();
    }
    private void display(){
        GetAllHotelsEmployeeInput input = GetAllHotelsEmployeeInput.builder()
                .id(SessionManager.getInstance().getLoggedInUser().getId())
                .build();
        Either<ErrorProcessor, GetAllHotelsEmployeeOutput> result = getAllHotelsProcess.process(input);
        result.fold(
                error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in displaying hotels",error.getMessage());
                    return null;
                },
                success -> {
                    data = FXCollections.observableArrayList(success.getHotelList());
                    table.setItems(data);
                    table.refresh();
                    return null;
                }
        );
        table.setItems(data);

        FilteredList<Hotel> filteredData=new FilteredList<>(data, b->true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(hotel -> {
                if(newValue.isBlank() || newValue.isEmpty() || newValue==null){
                    return true;
                }

                String searchKeyword=newValue.toLowerCase();

                if(hotel.getName().toLowerCase().contains(searchKeyword)){
                    return true;
                }
                else if(hotel.getLocation().toLowerCase().contains(searchKeyword)){
                    return true;
                }
                else return hotel.getStars().toString().toLowerCase().contains(searchKeyword);
            });
        });

        SortedList<Hotel> sortedList=new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList);

    }

    public void openHotelReservation(MouseEvent mouseEvent) throws IOException {
        if(mouseEvent.getClickCount()==2) {
            Hotel hotel = table.getSelectionModel().getSelectedItem();
            if(hotel==null) return;
            showMoreHotelData(hotel);
        }
        display();
    }

    private void showMoreHotelData(Hotel hotel) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tuvarna/hotel/rest/reception/display-more-info.fxml"));
        Parent root = loader.load();
        ReservationData controller = loader.getController();
        controller.setHotel(hotel);
        controller.display();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Hotel Information");
        stage.show();
    }
}
