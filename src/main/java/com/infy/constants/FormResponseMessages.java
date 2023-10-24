package com.infy.constants;

public enum FormResponseMessages {
    // Error Messages:
    FORM_NOT_FOUND("form.not.found"),
    FORM_ALREADY_EXISTS("form.already.exists");

    private final String message;

    FormResponseMessages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
