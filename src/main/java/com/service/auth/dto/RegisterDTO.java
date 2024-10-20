package com.service.auth.dto;

public record RegisterDTO(String name, String email, String password, UserRole role) {
}
