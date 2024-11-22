package com.service.auth.controllers;

import com.service.auth.dao.User;
import com.service.auth.dto.request.PromoteUserRequest;
import com.service.auth.dto.request.RegisterUserDto;
import com.service.auth.dto.response.PromoteUserResponse;
import com.service.auth.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admins")
@RestController
@AllArgsConstructor
public class AdminController {

    private final UserService userService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    @PostMapping("/createAdminUser")
    public ResponseEntity<User> createAdminUser(@RequestBody @Valid RegisterUserDto registerUserDto) {
        return ResponseEntity.ok(userService.signupAdminUser(registerUserDto));
    }

    @PostMapping("/promote")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public ResponseEntity<PromoteUserResponse> promoteUser(@RequestBody @Valid PromoteUserRequest request) throws Exception {

        PromoteUserResponse response = userService.promoteUser(request);

        return ResponseEntity.ok(response);
    }

}
