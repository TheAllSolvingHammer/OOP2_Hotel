package com.tuvarna.hotel.persistence.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum ReservationType {
    ONLINE,REAL,VIP_ONLINE,VIP_REAL,UNKNOWN;
    private static final Map<String,ReservationType> map= new HashMap<>();
    static {
        Arrays.stream(ReservationType.values())
                .filter(r->r!=UNKNOWN)
                .forEach(rt->map.put(rt.name(), rt));
    }

    public static ReservationType getByCode(String code){
        if(map.containsKey(code)) {
            return map.get(code);
        }
        return ReservationType.UNKNOWN;
    }
}
