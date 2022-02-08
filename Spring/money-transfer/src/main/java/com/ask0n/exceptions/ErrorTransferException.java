package com.ask0n.exceptions;

public class ErrorTransferException extends RuntimeException {
    public ErrorTransferException() {}

    public ErrorTransferException(String message) {
        super(message);
    }

    public ErrorTransferException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorTransferException(Throwable cause) {
        super(cause);
    }

    public ErrorTransferException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
