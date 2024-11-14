package com.service.auth.constants;

public enum ErrorStatusCode implements BaseEnum {
    UNEXPECTED("50000"),
    CONFIG("50001"),
    SUBSYSTEM("50002"),
    VALIDATION("50003"),
    CFHK("50004");

    private final String value;

    // Constructor
    ErrorStatusCode(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
