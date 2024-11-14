package com.service.auth.exceptions;

import com.service.auth.constants.ErrorType;

public class FunctionalException extends GeneralException {

    // Constructor
    public FunctionalException(ErrorType type, String message) {
        super(type, message != null ? message : type.getValue());
    }
}
