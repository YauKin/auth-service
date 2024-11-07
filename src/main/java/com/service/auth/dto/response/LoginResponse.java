package com.service.auth.dto.response;

public record LoginResponse(
        String token,
        long expiresIn
) {
}
