package com.techolution.user.util;

import com.techolution.user.payload.LoginRequest;
import com.techolution.user.payload.SignUpRequest;

public class ConstructObjectUtil {

    private ConstructObjectUtil() {
    }

    public static SignUpRequest constructSignUpRequest() {
        return SignUpRequest.builder()
                .firstName("Leanne")
                .lastName("Graham")
                .username("leanne")
                .password("password")
                .email("leanne.graham@gmail.com")
                .build();
    }

    public static LoginRequest constructLoginRequest() {
        return LoginRequest.builder()
                .usernameOrEmail("leanne")
                .password("password")
                .build();
    }
}
