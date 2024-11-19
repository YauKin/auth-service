package com.service.auth.controllers;

import com.service.auth.dao.User;
import com.service.auth.dto.request.PromoteUserRequest;
import com.service.auth.dto.response.PromoteUserResponse;
import com.service.auth.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<User>> allUsers() {
        List <User> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }

    @PostMapping("/promote")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public ResponseEntity<PromoteUserResponse> promoteUser(@RequestBody @Valid PromoteUserRequest request) throws Exception {

        PromoteUserResponse response = userService.promoteUser(request);

        return ResponseEntity.ok(response);
    }
}