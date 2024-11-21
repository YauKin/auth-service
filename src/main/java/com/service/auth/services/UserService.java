package com.service.auth.services;

import com.service.auth.constants.ErrorType;
import com.service.auth.constants.RoleEnum;
import com.service.auth.constants.Status;
import com.service.auth.dao.Role;
import com.service.auth.dao.User;
import com.service.auth.dto.request.PromoteUserRequest;
import com.service.auth.dto.request.RegisterUserDto;
import com.service.auth.dto.response.PromoteUserResponse;
import com.service.auth.exceptions.FunctionalException;
import com.service.auth.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    public List<User> allUsers() {
        return new ArrayList<>(userRepository.findAll());
    }

    public User signupUser(RegisterUserDto input) {
        return sign(input, RoleEnum.USER);
    }

    public User signupAdminUser(RegisterUserDto input) {
        return sign(input, RoleEnum.ADMIN);
    }

    public PromoteUserResponse promoteUser(PromoteUserRequest request) {
        try {
            User user = userRepository.findById(request.id()).orElseThrow(() -> new FunctionalException(ErrorType.USER_NOT_FOUND));
            if (user.getRole().getName() != RoleEnum.USER) {
                throw new FunctionalException(ErrorType.USER_ALREADY_ADMIN);
            }
            Role role = roleService.getRoleByName(RoleEnum.ADMIN);
            user.setRole(role);
            userRepository.save(user);
            return new PromoteUserResponse(Status.SUCCESS);
        } catch (FunctionalException e) {
            throw e;
        } catch (Exception e) {
            throw new FunctionalException(ErrorType.UNEXPECTED_ERROR, e.getMessage());
        }
    }

    public User sign(RegisterUserDto input, RoleEnum role) {
        // Check for existing email or username
        isEmailExists(input.email());
        try {
            return userRepository.save(User.builder()
                    .fullName(input.fullName())
                    .email(input.email())
                    .password(passwordEncoder.encode(input.password()))
                    .role(roleService.getRoleByName(role))
                    .build());
        } catch (FunctionalException e) {
            throw e;
        } catch (Exception e) {
            throw new FunctionalException(ErrorType.SIGNUP_FAILED);
        }
    }

    public void isEmailExists(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new FunctionalException(ErrorType.EMAIL_ALREADY_EXISTS);
        }
    }
}