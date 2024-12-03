package com.tuvarna.hotel.domain.singleton;

import java.util.HashMap;
import java.util.Map;

public class SingletonManager {
    private static final Map<Class<?>, Object> singletons = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T getInstance(Class<T> clazz) {
        if (!clazz.isAnnotationPresent(Singleton.class)) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " is not annotated with @Singleton");
        }
        return (T) singletons.computeIfAbsent(clazz, SingletonManager::createInstance);
    }

    private static Object createInstance(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create singleton instance for " + clazz.getName(), e);
        }
    }
}