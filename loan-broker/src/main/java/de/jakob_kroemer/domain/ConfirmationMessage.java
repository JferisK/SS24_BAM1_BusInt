package de.jakob_kroemer.domain;

public class ConfirmationMessage {
    private String message;

    public ConfirmationMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
