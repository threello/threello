package com.sparta.threello.enums;

public enum UserStatus {
    ACTIVATE("ACTIVATE"),
    DEACTIVATE("DEACTIVATE");

    private final String status;

    UserStatus(String status) {
        this.status = status;
    }
}
