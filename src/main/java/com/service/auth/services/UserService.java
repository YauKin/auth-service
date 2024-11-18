package com.service.auth.services;

import com.service.auth.dao.User;
import com.service.auth.dto.request.PromoteUserRequest;
import com.service.auth.dto.response.PromoteUserResponse;
import com.service.auth.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {
        return new ArrayList<>(userRepository.findAll());
    }

    private PromoteUserResponse promoteUser(PromoteUserRequest request) {
        //TODO: Check if user is already an admin or not found
        return null;
    }
}