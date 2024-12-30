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
        SingletonManager.getInstance(ConvertEntityToHotel.class);
        SingletonManager.getInstance(ConvertUsersToManager.class);
        SingletonManager.getInstance(ConvertUsersToOwner.class);
        SingletonManager.getInstance(ConvertServices.class);
        SingletonManager.getInstance(ConvertServicesToEntity.class);
        SingletonManager.getInstance(ConvertEntityToRoom.class);
        SingletonManager.getInstance(ConvertUsersToReceptionist.class);
        SingletonManager.getInstance(ConvertEntityToClient.class);
        //services
        SingletonManager.getInstance(CreateUserProcess.class);
        SingletonManager.getInstance(DisplayHotelProcess.class);
        SingletonManager.getInstance(DisplayManagersProcess.class);
        SingletonManager.getInstance(DisplayOwnersProcess.class);
        SingletonManager.getInstance(LoginUserProcess.class);
        SingletonManager.getInstance(CreateHotelProcess.class);
        SingletonManager.getInstance(DisplayServicesProcess.class);
        SingletonManager.getInstance(UpdateHotelProcess.class);
        SingletonManager.getInstance(DisplayOwnerHotelProcess.class);
        SingletonManager.getInstance(GetHotelManagersProcess.class);
        SingletonManager.getInstance(UpdateHotelProcess.class);
        SingletonManager.getInstance(DisplayManagerHotelProcess.class);
        SingletonManager.getInstance(CreateHotelProcess.class);
        SingletonManager.getInstance(GetRoomsPerHotelProcess.class);
        SingletonManager.getInstance(CreateRoomProcess.class);
        SingletonManager.getInstance(CreateServiceProcess.class);
        SingletonManager.getInstance(GetAllHotelsEmployeeProcess.class);
        SingletonManager.getInstance(DisplayAllEmployeeProcessor.class);
        SingletonManager.getInstance(GetAssignedEmployeesProcess.class);
        SingletonManager.getInstance(UpdateReceptionistOfHotelProcess.class);
        SingletonManager.getInstance(ClientInformationProcess.class);
        SingletonManager.getInstance(CreateReservationProcess.class);


    }
}
