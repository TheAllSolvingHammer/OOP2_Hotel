package com.tuvarna.hotel.rest.controllers.admin.owner;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.display.owner.DisplayOwnersInput;
import com.tuvarna.hotel.api.models.display.owner.DisplayOwnersOutput;
import com.tuvarna.hotel.api.models.entities.Owner;
import com.tuvarna.hotel.core.processes.DisplayOwnersProcess;
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

public class OwnerView implements Initializable{
    public TextField searchBar;
    private Stage stage;
    private Parent root;
    private Scene scene;
    private final DisplayOwnersProcess displayOwnersProcess;
    @FXML
    private TableView<Owner> table;
    @FXML
    private TableColumn<Owner, String> firstName;
    @FXML
    private TableColumn<Owner, String> lastName;
    @FXML
    private TableColumn<Owner, String> email;
    private ObservableList<Owner> data;

    public OwnerView() {
        displayOwnersProcess=SingletonManager.getInstance(DisplayOwnersProcess.class);
    }


    @FXML
    protected void switchToBeginning(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/admin/admin-view.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Admin");
        stage.show();
    }
    private void display(){
        DisplayOwnersInput input = DisplayOwnersInput.builder().build();
        Either<ErrorProcessor, DisplayOwnersOutput> result= displayOwnersProcess.process(input);
        result.fold(
                error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in displaying owners",error.getMessage());
                    return null;
                },
                success -> {
                    data = FXCollections.observableArrayList(success.getOwnerList());
                    table.setItems(data);
                    table.refresh();
                    return null;
                });

        table.setItems(data);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        display();

        FilteredList<Owner> filteredData=new FilteredList<>(data,b->true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(owner -> {
                if(newValue.isBlank() || newValue.isEmpty() || newValue==null){
                    return true;
                }

                String searchKeyword=newValue.toLowerCase();

                if(owner.getFirstName().toLowerCase().contains(searchKeyword)){
                    return true;
                }
                else if(owner.getLastName().toLowerCase().contains(searchKeyword)){
                    return true;
                }
                else return owner.getEmail().toLowerCase().contains(searchKeyword);
            });
        });

        SortedList<Owner> sortedList=new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList);


    }


    @FXML
    public void addOwners(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/admin/new-owner.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Owner creation");
        stage.show();
    }

    @FXML
    public void getOwnerFullInformation(MouseEvent mouseEvent) throws IOException {
        if(mouseEvent.getClickCount()==2) {
            Owner owner = table.getSelectionModel().getSelectedItem();
            if(owner==null) return;
            showMoreOwnerData(owner);
        }
    }

    public void showMoreOwnerData(Owner owner) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tuvarna/hotel/rest/admin/more-owner-info.fxml"));
        Parent root = loader.load();
        OwnerData controller = loader.getController();
        controller.setOwner(owner);
        controller.display();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Owner data");
        stage.setOnCloseRequest(windowEvent -> display());
        stage.setOnHidden(windowEvent -> display());
        stage.show();
    }

    public void clearTextField(ActionEvent actionEvent) {
        searchBar.clear();
    }
}
