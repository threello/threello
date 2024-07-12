package com.sparta.threello.enums;

public enum BoardMemberPermission {
    OWNER("OWNER"),
    MEMBER("MEMBER");

    private final String permission;

    BoardMemberPermission(String permission) {
        this.permission = permission;
    }
}
