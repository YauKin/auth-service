package com.service.auth.services;

import com.service.auth.constants.ErrorType;
import com.service.auth.constants.RoleEnum;
import com.service.auth.dao.Role;
import com.service.auth.dao.User;
import com.service.auth.dto.request.LoginUserDto;
import com.service.auth.dto.request.RegisterUserDto;
import com.service.auth.exceptions.FunctionalException;
import com.service.auth.repositories.RoleRepository;
import com.service.auth.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;


    public User signup(RegisterUserDto input) throws FunctionalException {
        // Check for existing email or username
        if (userRepository.existsByEmail(input.email())) {
            throw new FunctionalException(ErrorType.EMAIL_ALREADY_EXISTS);
        }
        Role role = roleRepository.findByName(RoleEnum.USER)
                .orElseThrow(() -> new FunctionalException(ErrorType.ROLE_NOT_FOUND));
        User signUpUser = User.builder()
                .fullName(input.fullName())
                .email(input.email())
                .password(passwordEncoder.encode(input.password()))
                .role(role)
                .build();
        try {
            return userRepository.save(signUpUser);
        } catch (Exception e) {
            log.error("Error while signing up user", e);
            throw new FunctionalException(ErrorType.SIGNUP_FAILED);
        }
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.email(),
                        input.password()
                )
        );

        return userRepository.findByEmail(input.email())
                .orElseThrow();
    }
}
