package com.tuvarna.hotel.api.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum StatusReservation {
    CONFIRMED,CANCELED,UNKNOWN;
    @Getter
    private static final Map<String,StatusReservation > map= new HashMap<>();
    static {
        Arrays.stream(StatusReservation .values())
                .filter(r->r!=UNKNOWN)
                .forEach(rt->map.put(rt.name(), rt));
    }

    public static StatusReservation  getByCode(String code){
        if(map.containsKey(code)) {
            return map.get(code);
        }
        return StatusReservation .UNKNOWN;
    }
}
