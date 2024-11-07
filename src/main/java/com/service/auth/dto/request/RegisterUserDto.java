package com.service.auth.dto.request;

import com.service.auth.dao.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterUserDto(
        @NotBlank(message = "The email is required.")
        @Email(message = "The email address is invalid.", flags = { Pattern.Flag.CASE_INSENSITIVE })
        String email,

        @NotBlank(message = "The password is required.")
        String password,

        @NotBlank(message = "The name is required.")
        String fullName

) {

        public static RegisterUserDto of(User user) {
                return new RegisterUserDto(
                        user.getEmail(),
                        user.getPassword(),
                        user.getFullName()
                );
        }

}
