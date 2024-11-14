package com.service.auth.exceptions;

import com.service.auth.constants.ErrorType;
import lombok.Getter;

@Getter
public abstract class GeneralException extends Exception {
    // Getter for type
    private final ErrorType type;

    // Constructor
    public GeneralException(ErrorType type, String message) {
        super(message != null ? message : type.getValue());
        this.type = type;
    }

}
