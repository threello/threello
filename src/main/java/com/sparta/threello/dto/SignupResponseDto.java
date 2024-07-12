package com.sparta.threello.dto;

import com.sparta.threello.entity.User;
import com.sparta.threello.enums.UserStatus;
import com.sparta.threello.enums.UserType;

import java.time.LocalDateTime;

public class SignupResponseDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String refreshToken;
    private UserStatus userStatus;
    private UserType userType;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public SignupResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.refreshToken = user.getRefreshToken();
        this.userStatus = user.getUserStatus();
        this.userType = user.getUserType();
        this.createdAt = user.getCreatedAt();
        this.modifiedAt = user.getModifiedAt();
    }

}
