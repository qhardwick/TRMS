package com.infy.constants;

public enum EmployeeResponseMessages {
    // Error messages:
    USER_NOT_FOUND("employee.not.found"),
    USER_ALREADY_EXISTS("employee.already.exists"),
    USER_NOT_LOGGED_IN("employee.login.must"),
    BENCO_REQUIRED("benco.required");

    private final String message;

    EmployeeResponseMessages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
