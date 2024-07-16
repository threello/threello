package com.sparta.threello.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    // user
    DUPLICATE_EMAIL(HttpStatus.LOCKED, "이미 존재하는 이메일입니다."),
    DEACTIVATE_USER(HttpStatus.LOCKED, "탈퇴한 회원입니다."),
    INVALID_PASSWORD(HttpStatus.LOCKED, "비밀번호가 일치하지 않습니다."),
    NOT_FOUND_USER(HttpStatus.LOCKED, "존재하지 않는 회원입니다."),
    NOT_AVAILABLE_PERMISSION(HttpStatus.LOCKED, "권한이 없습니다."),

    // board
    NOT_FOUND_BOARD(HttpStatus.LOCKED, "존재하지 않는 보드입니다."),
    NOT_CREATE_BOARD(HttpStatus.LOCKED, "생성한 보드가 없습니다."),
    NOT_EXIST_BOARDS(HttpStatus.LOCKED, "초대받은 보드가 없습니다."),
    ALREADY_INVITED_USER(HttpStatus.LOCKED, "이미 해당 유저가 보드에 초대 되어있습니다."),
    CANNOT_INVITE_SELF(HttpStatus.LOCKED, "자기 자신을 초대할 수 없습니다."),

    // deck(column)
    ALREADY_EXIST_DECK_TITLE(HttpStatus.NOT_FOUND, "이미 존재하는 컬럼 타이틀 입니다."),
    NOT_FOUND_DECK(HttpStatus.NOT_FOUND, "존재하지 않는 컬럼입니다."),

    // Card(column)
    NOT_FOUND_CARD(HttpStatus.NOT_FOUND, "존재하지 않는 카드입니다."),
    NOT_FOUND_CARDDETAIL(HttpStatus.NOT_FOUND, "존재하지 않는 카드 상세입니다."),
    NOT_FOUND_CARDMEMBER(HttpStatus.NOT_FOUND, "존재하지 않는 카드 멤버입니다."),
    NOT_FOUND_CARD_IN_THE_DECK(HttpStatus.NOT_FOUND,"해당 컬럼 안에 해당 카드가 없습니다."),
    NOT_FOUND_CARDMEMBER_IN_CARD(HttpStatus.NOT_FOUND, "해당 카드 안에 해당 카드 멤버가 없습니다."),
    // comment
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다."),
    NOT_FOUND_COMMENT_IN_CARD(HttpStatus.NOT_FOUND, "해당 카드 안에 해당 댓글이 없습니다."),

    // JWT
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다. 다시 로그인 해주세요."),
    NOT_FOUND_AUTHENTICATION_INFO(HttpStatus.NOT_FOUND, "인증 정보를 찾을 수 없습니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다. 다시 로그인 해주세요."),
    LOGGED_OUT_TOKEN(HttpStatus.FORBIDDEN, "이미 로그아웃된 토큰입니다."),
    INVALID_JWT(HttpStatus.UNAUTHORIZED, "유효하지 않는 JWT 입니다."),
    EXPIRED_JWT(HttpStatus.FORBIDDEN, "만료된 JWT 입니다."),
    REQUIRES_LOGIN(HttpStatus.FORBIDDEN, "로그인이 필요한 서비스입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}