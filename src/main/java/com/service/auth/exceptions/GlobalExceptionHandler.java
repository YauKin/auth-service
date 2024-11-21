package com.service.auth.exceptions;

import com.service.auth.constants.ErrorType;
import com.service.auth.dto.response.GeneralErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.Date;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
        // Determine the actual exception to handle
        Exception actualException = (ex instanceof UndeclaredThrowableException)
                ? (Exception) ((UndeclaredThrowableException) ex).getUndeclaredThrowable()
                : ex;

        // Determine the HTTP status based on the exception type
        HttpStatus status = (actualException instanceof GeneralException)
                ? ((GeneralException) actualException).getType().getHttpStatus()
                : HttpStatus.INTERNAL_SERVER_ERROR;
        // Log the exception
        log.error(ex.getMessage(), ex);
        // Create a GeneralErrorResponse object
        GeneralErrorResponse errorResponse = createErrorResponse(actualException, status);

        return handleExceptionInternal(actualException, errorResponse, new HttpHeaders(), status, request);
    }


    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private GeneralErrorResponse createErrorResponse(Exception ex, HttpStatus status) {
        String errorTypeValue = (ex instanceof GeneralException)
                ? ((GeneralException) ex).getType().getValue()
                : ErrorType.NOT_SPECIFIED.getValue();

        return new GeneralErrorResponse(
                status,
                new Date(),
                errorTypeValue,
                ex.getMessage()
        );
    }

}