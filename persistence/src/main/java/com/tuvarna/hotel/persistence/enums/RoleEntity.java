package com.tuvarna.hotel.persistence.enums;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum RoleEntity {
    ADMINISTRATOR,MANAGER,EMPLOYEE,OWNER,UNKNOWN;
    private static final Map<String, RoleEntity> map= new HashMap<>();

    static {
        Arrays.stream(RoleEntity.values())
                .filter(r->r!=UNKNOWN)
                .forEach(rt -> map.put(rt.name(), rt));
    }

    public static RoleEntity getByCode(String code){
        if(map.containsKey(code)) {
            return map.get(code);
        }
        return RoleEntity.UNKNOWN;
    }
}
