package com.tuvarna.hotel.api.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum TypeRoom {
    SINGLE,DOUBLE,APARTMENT,MAISONETTE,PRESIDENT,UNKNOWN;
    @Getter
    private static final Map<String,TypeRoom> map= new HashMap<>();
    static {
        Arrays.stream(TypeRoom.values())
                .filter(r->r!=UNKNOWN)
                .forEach(rt->map.put(rt.name(), rt));
    }

    public static TypeRoom getByCode(String code){
        if(map.containsKey(code)) {
            return map.get(code);
        }
        return TypeRoom.UNKNOWN;
    }

}
