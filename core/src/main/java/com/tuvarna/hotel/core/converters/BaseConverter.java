package com.tuvarna.hotel.core.converters;

public interface BaseConverter<T,E> {
    E convert(T t);
}
