package com.sparta.threello.dto;

import com.sparta.threello.entity.User;
import com.sparta.threello.enums.UserType;
import lombok.Getter;

@Getter
public class UserProfileResponseDto {
    private Long id;
    private String name;
    private String email;
    private UserType userType;

    public UserProfileResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.userType = user.getUserType();
    }
}