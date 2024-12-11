package com.tuvarna.hotel.domain.encoder;

public interface PasswordEncoder {
    String encode(CharSequence rawPassword);
    boolean matches(CharSequence rawPassword, String encodedPassword);
    static PasswordEncoder defaultEncoder() {
        return new MyPasswordEncoder();
    }
}
