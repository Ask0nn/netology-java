package com.ask0n.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UnauthorizedUser extends RuntimeException {
    public UnauthorizedUser(String message) {
        super(message);
        System.err.println(message);
    }
}
