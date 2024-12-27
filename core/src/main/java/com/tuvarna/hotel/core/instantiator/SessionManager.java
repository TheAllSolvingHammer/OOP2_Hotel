package com.tuvarna.hotel.core.instantiator;

import com.tuvarna.hotel.persistence.entities.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SessionManager {

    private static SessionManager instance;
    private UserEntity loggedInUser;

    private SessionManager() {

    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }


    public static void clearSession() {
        if (instance != null) {
            instance.loggedInUser = null;
        }
    }
}
