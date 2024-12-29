package com.tuvarna.hotel.persistence.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum RoomType {
    SINGLE,DOUBLE,APARTMENT,MAISONETTE,PRESIDENT,UNKNOWN;
    private static final Map<String,RoomType> map= new HashMap<>();
    static {
        Arrays.stream(RoomType.values())
                .filter(r->r!=UNKNOWN)
                .forEach(rt->map.put(rt.name(), rt));
    }

    public static RoomType getByCode(String code){
        if(map.containsKey(code)) {
            return map.get(code);
        }
        return RoomType.UNKNOWN;
    }
}
