package com.ask0n.cloudvault.controllers;

import com.ask0n.cloudvault.exceptions.NotFoundException;
import com.ask0n.cloudvault.models.Error;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionsController {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Error> defaultErrorHandler(Exception e) throws Exception {
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
            throw e;

        if (AnnotationUtils.findAnnotation(e.getClass(), ExceptionHandler.class) != null)
            throw e;

        return new ResponseEntity<>(new Error(e.getMessage(), 500000), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Error> handleNotFound(NotFoundException e) {
        return new ResponseEntity<>(new Error(e.getMessage(), 404001), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<Error> handleBadCredentials(BadCredentialsException e) {
        return new ResponseEntity<>(new Error(e.getMessage(), 400002), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ExpiredJwtException.class)
    public ResponseEntity<Error> handleUnauthorized(ExpiredJwtException e) {
        return new ResponseEntity<>(new Error(e.getMessage(), 401003), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = UnsupportedJwtException.class)
    public ResponseEntity<Error> handleUnauthorized(UnsupportedJwtException e) {
        return new ResponseEntity<>(new Error(e.getMessage(), 401003), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = MalformedJwtException.class)
    public ResponseEntity<Error> handleUnauthorized(MalformedJwtException e) {
        return new ResponseEntity<>(new Error(e.getMessage(), 401003), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = SignatureException.class)
    public ResponseEntity<Error> handleUnauthorized(SignatureException e) {
        return new ResponseEntity<>(new Error(e.getMessage(), 401003), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = IOException.class)
    public ResponseEntity<Error> handleIOException(IOException e) {
        return new ResponseEntity<>(new Error(e.getMessage(), 400004), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<Error> handleNullPointerException(NullPointerException e) {
        return new ResponseEntity<>(new Error(e.getMessage(), 400004), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<Error> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(new Error(e.getMessage(), 400006), HttpStatus.BAD_REQUEST);
    }
}
