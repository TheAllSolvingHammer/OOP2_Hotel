package com.tuvarna.hotel.api.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum TypeReservation {
    ONLINE,REAL,VIP_ONLINE,VIP_REAL,UNKNOWN;
    private static final Map<String,TypeReservation> map= new HashMap<>();
    static {
        Arrays.stream(TypeReservation.values())
                .filter(r->r!=UNKNOWN)
                .forEach(rt->map.put(rt.name(), rt));
    }

    public static TypeReservation getByCode(String code){
        if(map.containsKey(code)) {
            return map.get(code);
        }
        return TypeReservation.UNKNOWN;
    }
}
