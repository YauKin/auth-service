package com.service.auth.exceptions;

import com.service.auth.constants.ErrorType;
import com.service.auth.dto.response.GeneralErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
//    @ExceptionHandler(value = Exception.class)
//    public ResponseEntity<Object> handleAllException(Exception ex) {
//        GeneralErrorResponse er = new GeneralErrorResponse();
//        er.setCode(ex.getClass().getSimpleName());
//        HttpStatus status;
//        if (ex instanceof GeneralException) {
//
//        }
//
//        if (ex instanceof ResourceNotFoundException) {
//            status = HttpStatus.NOT_FOUND;
//        } else if (ex instanceof NoSuchElementException) {
//            status = HttpStatus.NOT_FOUND;
//            er.setMessage("Record not found");
//        } else if (ex instanceof ConstraintViolationException) {
//            status = HttpStatus.UNPROCESSABLE_ENTITY;
//            er.setMessage(((ConstraintViolationException) ex).getConstraintViolations()
//                    .stream()
//                    .map(cv -> cv.getPropertyPath().toString() + ": " + cv.getMessage())
//                    .collect(Collectors.joining(", ")));
//        } else if (ex instanceof MethodArgumentNotValidException) {
//            status = HttpStatus.UNPROCESSABLE_ENTITY;
//            er.setMessage(((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors()
//                    .stream().map(error -> {
//                        if (error instanceof FieldError) {
//                            return ((FieldError) error).getObjectName() + "." + ((FieldError) error).getField() + ": " + error.getDefaultMessage();
//                        } else {
//                            return error.getDefaultMessage();
//                        }
//                    }).collect(Collectors.joining(", ")));
//        } else if (ex instanceof BindException) {
//            status = HttpStatus.BAD_REQUEST;
//            er.setMessage(((BindException) ex).getBindingResult().getAllErrors()
//                    .stream().map(error -> {
//                        if (error instanceof FieldError) {
//                            return ((FieldError) error).getObjectName() + "." + ((FieldError) error).getField() + ": " + error.getDefaultMessage();
//                        } else {
//                            return error.getDefaultMessage();
//                        }
//                    }).collect(Collectors.joining(", ")));
//        } else if (ex instanceof TypeMismatchException) {
//            status = HttpStatus.BAD_REQUEST;
//            er.setMessage("Invalid value '" + ((TypeMismatchException) ex).getValue().toString() + "'");
//        } else if (ex instanceof MissingPathVariableException || ex instanceof HttpMessageNotReadableException || ex instanceof HttpMessageNotWritableException) {
//            status = HttpStatus.BAD_REQUEST;
//        } else if (ex instanceof NoHandlerFoundException) {
//            status = HttpStatus.NOT_FOUND;
//        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
//            status = HttpStatus.METHOD_NOT_ALLOWED;
//        } else if (ex instanceof HttpMediaTypeNotSupportedException) {
//            status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
//        } else if (ex instanceof HttpMediaTypeNotAcceptableException) {
//            status = HttpStatus.NOT_ACCEPTABLE;
//        } else if (ex instanceof AsyncRequestTimeoutException) {
//            status = HttpStatus.SERVICE_UNAVAILABLE;
//        } else {
//            ex.printStackTrace();
//            status = HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//        er.setStatus(status);
//        return new ResponseEntity<>(er, status);
//    }

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

        // Call the handleExceptionInternal method with the resolved parameters
        return handleExceptionInternal(actualException, null, new HttpHeaders(), status, request);
    }

    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        // Log the exception message and stack trace
        System.out.println(ex.getMessage());
        ex.printStackTrace();

        // Create a GeneralErrorResponse object
        String errorTypeValue = (ex instanceof GeneralException)
                ? ((GeneralException) ex).getType().getValue()
                : ErrorType.NOT_SPECIFIED.getValue();

        GeneralErrorResponse errorResponse = new GeneralErrorResponse(
                status,
                new Date(),
                errorTypeValue,
                ex.getMessage()
        );

        // Call the superclass's handleExceptionInternal method
        return super.handleExceptionInternal(ex, errorResponse, headers, status, request);
        }

}