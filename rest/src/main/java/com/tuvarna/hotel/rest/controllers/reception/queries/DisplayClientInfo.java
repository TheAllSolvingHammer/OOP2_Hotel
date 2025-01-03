package com.tuvarna.hotel.rest.controllers.reception.queries;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.entities.Client;
import com.tuvarna.hotel.api.models.query.client.information.ClientInformationInput;
import com.tuvarna.hotel.api.models.query.client.information.ClientInformationOutput;
import com.tuvarna.hotel.core.processes.ClientInformationProcess;
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

public class DisplayClientInfo implements Initializable {
    @FXML
    private TableView<Client> table;
    @FXML
    private TableColumn<Client,String> phone;
    @FXML
    private TableColumn<Client,String> address;
    @FXML
    private TableColumn<Client,String> ucn;
    private Stage stage;
    private Parent root;
    private Scene scene;
    @FXML
    public TextField searchBar;
    @FXML
    private TableColumn<Client,String> firstName;
    @FXML
    private TableColumn<Client,String> lastName;
    @FXML
    private TableColumn<Client,String> email;
    @FXML
    private TableColumn<Client,String> clientRating;
    private ObservableList<Client> data;

    private final ClientInformationProcess clientInformationProcess;

    public DisplayClientInfo() {
        clientInformationProcess = SingletonManager.getInstance(ClientInformationProcess.class);
    }

    public void clearTextField(ActionEvent event) {
        searchBar.clear();
    }

    @FXML
    public void switchToBeginning(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/reception/receptionist-view.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void display(){
        ClientInformationInput input=ClientInformationInput.builder().build();

        Either<ErrorProcessor,ClientInformationOutput> result = clientInformationProcess.process(input);

        result.fold(
                error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in displaying clients",error.getMessage());
                    return null;
                },
                success ->{
                    data= FXCollections.observableArrayList(success.getClientList());
                    table.setItems(data);
                    table.refresh();
                    return null;
                }
        );
        table.setItems(data);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        ucn.setCellValueFactory(new PropertyValueFactory<>("ucn"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        clientRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        display();


        FilteredList<Client> filteredData = new FilteredList<>(data, b -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(client -> {
                if (newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if(client.getFirstName().toLowerCase().contains(searchKeyword)){
                    return true;
                }
                else if(client.getLastName().toLowerCase().contains(searchKeyword)){
                    return true;
                }
                else if(client.getEmail().toLowerCase().contains(searchKeyword)){
                    return true;
                }
                else if(client.getPhone().toLowerCase().contains(searchKeyword)){
                    return true;
                }
                else if(client.getAddress().toLowerCase().contains(searchKeyword)){
                    return true;
                }
                else if (client.getRating().toString().toLowerCase().contains(searchKeyword)) {
                    return true;
                }
                else return client.getUcn().toLowerCase().contains(searchKeyword);
            });
        });
        SortedList<Client> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);

    }

    public void openClientInformation(MouseEvent mouseEvent) throws IOException {
        if(mouseEvent.getClickCount() == 2){
            Client client = table.getSelectionModel().getSelectedItem();
            if(client == null) return;
            showMoreClientData(client);
        }
    }

    private void showMoreClientData(Client client) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tuvarna/hotel/rest/reception/update-client-info.fxml"));
        Parent root = loader.load();
        DisplayClientData controller = loader.getController();
        controller.setClient(client);
        controller.display();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Hotel Information");
        stage.setOnCloseRequest(windowEvent -> display());
        stage.setOnHidden(windowEvent -> display());
        stage.show();
    }
}
