package com.service.auth.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum ErrorType implements BaseEnum {
    NOT_SPECIFIED("NOT_SPECIFIED", HttpStatus.OK),

    INVALID_REQUEST("INVALID_REQUEST", HttpStatus.BAD_REQUEST),
    MALFORMED_DATA("MALFORMED_DATA", HttpStatus.BAD_REQUEST),
    MEMBER_NOT_FOUND("MEMBER_NOT_FOUND", HttpStatus.BAD_REQUEST),

    NOT_FOUND("NOT_FOUND", HttpStatus.UNPROCESSABLE_ENTITY),
    INVALID_TOKEN("INVALID_TOKEN", HttpStatus.UNPROCESSABLE_ENTITY);

    private final String value;
    private final HttpStatus httpStatus;
    private final ErrorStatusCode statusCode;

    // Constructor
    ErrorType(String value, HttpStatus httpStatus) {
        this(value, httpStatus, ErrorStatusCode.UNEXPECTED); // Default status code
    }

}