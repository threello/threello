package com.sparta.threello.entity;

import com.sparta.threello.dto.SignupRequestDto;
import com.sparta.threello.dto.SignupResponseDto;
import com.sparta.threello.enums.UserStatus;
import com.sparta.threello.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Setter
    @Column
    private String refreshToken;

    //회원 상태 (ACTIVATE-활동 / DEACTIVATE-탈퇴)
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserStatus userStatus;

    //회원 권한 (MANAGER-보드생성 가능 / USER-보드생성 불가능)
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserType userType;

    //BoardMember 과 조인
    @OneToMany(mappedBy = "user"/*,cascade = CascadeType.ALL, orphanRemoval = true*/)
    private List<BoardMember> boardMemberList = new ArrayList<>();

    //refreshToken 확인
    public boolean checkRefreshToken(String refreshToken) {
        return refreshToken.equals(this.refreshToken);
    }

    //refreshToken 저장
    public void saveRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }


    public User(SignupRequestDto requestDto, String encodedPassword) {
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
        this.password = encodedPassword;
        this.userStatus = UserStatus.ACTIVATE;
        this.userType = requestDto.getUserType();
    }

    public void deactivateUser() {
        this.userStatus = UserStatus.DEACTIVATE;
    }
}