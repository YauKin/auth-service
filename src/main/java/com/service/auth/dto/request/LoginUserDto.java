package com.service.auth.dto.request;

import com.service.auth.dao.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record LoginUserDto(
        @NotEmpty(message = "The email address is required.")
        @Email(message = "The email address is invalid.", flags = {Pattern.Flag.CASE_INSENSITIVE})
        String email,
        @NotEmpty(message = "The email address is required.")
        String password

) {
        public static LoginUserDto of(User user) {
                return new LoginUserDto(
                        user.getEmail(),
                        user.getPassword()
                );
        }
}
