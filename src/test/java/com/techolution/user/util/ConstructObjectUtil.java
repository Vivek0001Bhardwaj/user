package com.techolution.user.util;

import com.techolution.user.model.user.User;
import com.techolution.user.payload.InfoRequest;
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

    public static User constructUser() {
        return User.builder()
                .firstName("Ervin")
                .lastName("Howell")
                .username("ervin")
                .password("password")
                .email("ervin.howell@gmail.com")
                .phone("12312")
                .build();
    }

    public static InfoRequest constructInfoRequest() {
        return InfoRequest.builder()
                .street("Douglas Extension")
                .suite("Suite 847")
                .city("McKenziehaven")
                .zipcode("59590-4157")
                .build();
    }
}
