package com.tuvarna.hotel.api.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum RoleType {
    ADMINISTRATOR,MANAGER,EMPLOYEE,OWNER,UNKNOWN;

    private static final Map<String,RoleType> map= new HashMap<>();
    static {
        Arrays.stream(RoleType.values())
                .filter(r->r!=UNKNOWN)
                .forEach(rt->map.put(rt.name(), rt));
    }

    public static RoleType getByCode(String code){
        if(map.containsKey(code)) {
            return map.get(code);
        }
        return RoleType.UNKNOWN;
    }
}
