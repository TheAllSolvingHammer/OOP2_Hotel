package com.tuvarna.hotel.core.instantiator;

import com.tuvarna.hotel.core.converters.ConvertServicesToList;
import com.tuvarna.hotel.core.converters.ConvertUsersToManager;
import com.tuvarna.hotel.core.converters.ConvertUsersToOwner;
import com.tuvarna.hotel.core.processes.*;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.*;

public class Instantiation {

    public static void loadInstances(){
        SingletonManager.getInstance(UserRepositoryImpl.class);
        SingletonManager.getInstance(ServiceRepositoryImpl.class);
        SingletonManager.getInstance(HotelRepositoryImpl.class);
        SingletonManager.getInstance(ClientRepositoryImpl.class);
        SingletonManager.getInstance(RoomRepositoryImpl.class);
        SingletonManager.getInstance(ReservationRepositoryImpl.class);
        //todo add rest
        //converters
        SingletonManager.getInstance(ConvertServicesToList.class);
        SingletonManager.getInstance(ConvertUsersToManager.class);
        SingletonManager.getInstance(ConvertUsersToOwner.class);
        //services
        SingletonManager.getInstance(CreateUserProcess.class);
        SingletonManager.getInstance(DisplayHotelProcess.class);
        SingletonManager.getInstance(DisplayManagersProcess.class);
        SingletonManager.getInstance(DisplayOwnersProcess.class);
        SingletonManager.getInstance(LoginUserProcess.class);
        SingletonManager.getInstance(CreateHotelProcess.class);
    }
}
