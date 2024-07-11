package com.sparta.threello.enums;

public enum CardStatus {
    PROCESSING("PROCESSING"),
    COMPLETED("COMPLETED");

    private final String status;

    CardStatus(String status) {
        this.status = status;
    }
}
