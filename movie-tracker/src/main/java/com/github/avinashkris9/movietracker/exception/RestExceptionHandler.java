package com.github.avinashkris9.movietracker.exception;

import com.github.avinashkris9.movietracker.model.APIError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status,
        WebRequest request) {

        APIError apiError = new APIError();
        apiError.setCode(" todo");
        apiError.setMessage("to do ");
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<APIError> handleNotFoundException(Exception exception) {

        System.out.println(" I am here I am here");
        APIError apiError = new APIError();
        apiError.setCode(exception.getMessage());
        apiError.setMessage(exception.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(value = EntityExistsException.class)
    public ResponseEntity<APIError> EntityExistsExceptionHandler(Exception exception) {

        APIError apiError = new APIError();
        apiError.setCode(exception.getMessage());
        apiError.setMessage(exception.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);

    }

}
