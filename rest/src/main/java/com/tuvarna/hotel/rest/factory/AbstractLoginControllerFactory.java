package com.tuvarna.hotel.rest.factory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//todo singleton
public class AbstractLoginControllerFactory {
    private final Map<String, String> controllerMap;


    public AbstractLoginControllerFactory(){
        controllerMap = new HashMap<>();
        controllerMap.put("ADMINISTRATOR", "../admin/admin-view.fxml");
        controllerMap.put("OWNER", "../owner/owner-view.fxml");
        controllerMap.put("RECEPTIONIST","../reception/receptionist-view.fxml");
        controllerMap.put("USER","../manager/manager-view.fxml");
        //tuka dobavqme razlichnite sceni
        // ako iskash da dostupish nqkoq scena veche mojesh , prosto v logina ima prenasochvane
        // ot men tolkoz

    }
    public Parent getController(String roleType) throws IllegalAccessException, IOException {

        if(!controllerMap.containsKey(roleType)){
            throw new RuntimeException("No such controller found");
        }
        return FXMLLoader.load(Objects.requireNonNull(getClass().getResource(controllerMap.get(roleType))));
    }
}
