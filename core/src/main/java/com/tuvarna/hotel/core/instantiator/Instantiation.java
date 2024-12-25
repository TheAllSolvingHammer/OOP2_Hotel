package com.tuvarna.hotel.core.instantiator;

import com.tuvarna.hotel.core.converters.*;
import com.tuvarna.hotel.core.processes.*;
import com.tuvarna.hotel.domain.encoder.MyPasswordEncoder;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.*;

public class Instantiation {

    public static void loadInstances(){
        SingletonManager.getInstance(MyPasswordEncoder.class);

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
        SingletonManager.getInstance(ConvertServices.class);
        SingletonManager.getInstance(ConvertServicesToEntity.class);
        //services
        SingletonManager.getInstance(CreateUserProcess.class);
        SingletonManager.getInstance(DisplayHotelProcess.class);
        SingletonManager.getInstance(DisplayManagersProcess.class);
        SingletonManager.getInstance(DisplayOwnersProcess.class);
        SingletonManager.getInstance(LoginUserProcess.class);
        SingletonManager.getInstance(CreateHotelProcess.class);
        SingletonManager.getInstance(DisplayServicesProcess.class);
        SingletonManager.getInstance(UpdateServicesProcess.class);
        SingletonManager.getInstance(DisplayOwnerHotelProcess.class);
        SingletonManager.getInstance(GetUnassignedManagersProcess.class);
        SingletonManager.getInstance(UpdateManagersProcess.class);
    }
}
