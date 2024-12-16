package com.tuvarna.hotel.api.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum RoleType {
    ADMINISTRATOR("admin"),MANAGER("manager"),EMPLOYEE("employee"),OWNER("owner"),UNKNOWN(null);
    private final String val;
    private static final Map<String, RoleType> map= new HashMap<>();
    static {
        Arrays.stream(RoleType.values())
                .filter(r->r!=UNKNOWN)
                .forEach(rt -> map.put(rt.toString(), rt));
    }
    RoleType(String s) {
        this.val =s;
    }

    public String toString(){
        return val;
    }

    public static RoleType getByCode(String code){
        if(map.containsKey(code)) {
            return map.get(code);
        }
        return RoleType.UNKNOWN;
    }
}
