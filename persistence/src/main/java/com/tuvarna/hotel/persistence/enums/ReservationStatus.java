package com.tuvarna.hotel.persistence.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum ReservationStatus {
    CONFIRMED,CANCELED,UNKNOWN;
    private static final Map<String,ReservationStatus> map= new HashMap<>();
    static {
        Arrays.stream(ReservationStatus.values())
                .filter(r->r!=UNKNOWN)
                .forEach(rt->map.put(rt.name(), rt));
    }

    public static ReservationStatus getByCode(String code){
        if(map.containsKey(code)) {
            return map.get(code);
        }
        return ReservationStatus.UNKNOWN;
    }
}
