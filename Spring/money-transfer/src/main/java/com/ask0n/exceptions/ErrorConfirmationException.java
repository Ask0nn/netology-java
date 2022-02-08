package com.ask0n.exceptions;

public class ErrorConfirmationException extends RuntimeException {
    public ErrorConfirmationException() {}

    public ErrorConfirmationException(String message) {
        super(message);
    }

    public ErrorConfirmationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorConfirmationException(Throwable cause) {
        super(cause);
    }

    public ErrorConfirmationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
