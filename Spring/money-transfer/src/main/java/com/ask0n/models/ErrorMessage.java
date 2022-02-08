package com.ask0n.models;

public class ErrorMessage {
    private String message;
    private int id;

    public ErrorMessage() {}
    public ErrorMessage(String message, int id) {
        this.message = message;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
