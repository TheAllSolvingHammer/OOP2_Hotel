package com.tuvarna.hotel.api.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum RatingClient {
    NORMAL,CONCERNING,BAD,UNKNOWN;
    @Getter
    private static final Map<String,RatingClient> map= new HashMap<>();
    static {
        Arrays.stream(RatingClient.values())
                .filter(r->r!=UNKNOWN)
                .forEach(rt->map.put(rt.name(), rt));
    }

    public static RatingClient getByCode(String code){
        if(map.containsKey(code)) {
            return map.get(code);
        }
        return RatingClient.UNKNOWN;
    }
}
