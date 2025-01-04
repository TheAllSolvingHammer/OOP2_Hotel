package com.tuvarna.hotel.persistence.initializer;

import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.ServiceRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.ServiceEntity;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class InitializeServices {
    private static final Logger log=Logger.getLogger(InitializeServices.class);
    private final ServiceRepositoryImpl serviceRepository;

    public InitializeServices() {
        serviceRepository = SingletonManager.getInstance(ServiceRepositoryImpl.class);
    }

    public void initializeData() {
        if (hasExistingServices()) {
            return;
        }

        List<ServiceEntity> defaultServices = getDefaultServices();
        for (ServiceEntity service : defaultServices) {
            serviceRepository.save(service);
        }
        log.info("Adding additional services");

    }

    private boolean hasExistingServices() {
        List<ServiceEntity> services = serviceRepository.getAll();
        return services != null && !services.isEmpty();
    }

    private List<ServiceEntity> getDefaultServices() {
        return Arrays.asList(
                ServiceEntity.builder().serviceName("All-Inclusive").price(BigDecimal.valueOf(300.00)).build(),
                ServiceEntity.builder().serviceName("Spa").price(BigDecimal.valueOf(150.00)).build(),
                ServiceEntity.builder().serviceName("Swimming Pool").price(BigDecimal.valueOf(50.00)).build(),
                ServiceEntity.builder().serviceName("Gym").price(BigDecimal.valueOf(70.00)).build(),
                ServiceEntity.builder().serviceName("Room Service").price(BigDecimal.valueOf(30.00)).build(),
                ServiceEntity.builder().serviceName("Laundry Service").price(BigDecimal.valueOf(40.00)).build(),
                ServiceEntity.builder().serviceName("WiFi").price(BigDecimal.valueOf(10.00)).build(),
                ServiceEntity.builder().serviceName("Parking").price(BigDecimal.valueOf(20.00)).build()
        );
    }
}
