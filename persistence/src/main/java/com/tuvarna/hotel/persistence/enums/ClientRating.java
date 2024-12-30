package com.tuvarna.hotel.persistence.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum ClientRating {
    NORMAL,CONCERNING,BAD,UNKNOWN;
    private static final Map<String,ClientRating> map= new HashMap<>();
    static {
        Arrays.stream(ClientRating.values())
                .filter(r->r!=UNKNOWN)
                .forEach(rt->map.put(rt.name(), rt));
    }

    public static ClientRating getByCode(String code){
        if(map.containsKey(code)) {
            return map.get(code);
        }
        return ClientRating.UNKNOWN;
    }
}
