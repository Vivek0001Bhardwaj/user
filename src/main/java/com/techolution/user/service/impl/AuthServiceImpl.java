package com.techolution.user.service.impl;

import com.techolution.user.exception.AppException;
import com.techolution.user.exception.AuthException;
import com.techolution.user.exception.UserApiException;
import com.techolution.user.model.role.Role;
import com.techolution.user.model.role.RoleName;
import com.techolution.user.model.user.User;
import com.techolution.user.payload.SignUpRequest;
import com.techolution.user.repository.RoleRepository;
import com.techolution.user.repository.UserRepository;
import com.techolution.user.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static com.techolution.user.utils.AppConstants.USER_ROLE_NOT_SET;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public URI registerUser(SignUpRequest signUpRequest) {
        try {
            if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpRequest.getUsername()))) {
                throw new UserApiException(HttpStatus.BAD_REQUEST, "Username is already taken");
            }

            if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))) {
                throw new UserApiException(HttpStatus.BAD_REQUEST, "Email is already taken");
            }

            User user = constructUser(signUpRequest);

            List<Role> roles = new ArrayList<>();

            if (userRepository.count() == 0) {
                roles.add(roleRepository.findByName(RoleName.ROLE_USER)
                        .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));
                roles.add(roleRepository.findByName(RoleName.ROLE_ADMIN)
                        .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));
            } else {
                roles.add(roleRepository.findByName(RoleName.ROLE_USER)
                        .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));
            }

            user.setRoles(roles);

            User result = userRepository.save(user);

            return ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{userId}")
                    .buildAndExpand(result.getId()).toUri();
        } catch (Exception e) {
            log.error("Exception occurred while registering the user : {}", ExceptionUtils.getStackTrace(e));
            throw new AuthException("Exception occurred while registering the user : "+e.getMessage());
        }
    }

    private User constructUser(SignUpRequest signUpRequest) {
        return User.builder()
                .firstName(signUpRequest.getFirstName().toLowerCase())
                .lastName(signUpRequest.getLastName().toLowerCase())
                .username(signUpRequest.getUsername().toLowerCase())
                .email(signUpRequest.getEmail().toLowerCase())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .build();
    }
}
