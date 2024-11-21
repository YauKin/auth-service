package com.service.auth.services;

import com.service.auth.constants.ErrorType;
import com.service.auth.constants.RoleEnum;
import com.service.auth.constants.Status;
import com.service.auth.dao.Role;
import com.service.auth.dao.User;
import com.service.auth.dto.request.PromoteUserRequest;
import com.service.auth.dto.response.PromoteUserResponse;
import com.service.auth.exceptions.FunctionalException;
import com.service.auth.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final RoleService roleService;

    public List<User> allUsers() {
        return new ArrayList<>(userRepository.findAll());
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
            log.error("Functional error while promoting user", e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while promoting user", e);
            throw new FunctionalException(ErrorType.UNEXPECTED_ERROR, e.getMessage());
        }
    }
}