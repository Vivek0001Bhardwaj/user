package com.techolution.user.service;

import com.techolution.user.payload.SignUpRequest;

import java.net.URI;

public interface AuthService {
    URI registerUser(SignUpRequest signUpRequest);
}
