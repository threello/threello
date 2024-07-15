package com.sparta.threello.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseStatus {
    // 회원가입
    SIGN_UP_SUCCESS(HttpStatus.OK, "회원가입에 성공하였습니다."),
    DEACTIVATE_USER_SUCCESS(HttpStatus.OK, "회원탈퇴에 성공하였습니다."),

    // 로그인
    LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공하였습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃에 성공하였습니다."),
    GET_USER_SUCCESS(HttpStatus.OK, "유저정보 조회에 성공하였습니다."),

    // 보드
    BOARD_CREATE_SUCCESS(HttpStatus.OK, "보드 생성에 성공하였습니다."),
    BOARDS_READ_SUCCESS(HttpStatus.OK, "전체 보드 조회에 성공하였습니다."),
    BOARD_READ_SUCCESS(HttpStatus.OK, "보드 조회에 성공하였습니다."),
    BOARD_UPDATE_SUCCESS(HttpStatus.OK, "보드 수정에 성공하였습니다."),
    BOARD_DELETE_SUCCESS(HttpStatus.OK, "보드가 삭제되었습니다."),
    BOARD_POSITION_UPDATE_SUCCESS(HttpStatus.OK, "보드 포지션 변경에 성공하였습니다."),
    BOARD_INVITE_MEMBER_SUCCESS(HttpStatus.OK, "보드에 사용자 초대가 완료되었습니다."),

    // 덱(컬럼)
    DECK_CREATE_SUCCESS(HttpStatus.OK, "컬럼 생성에 성공하였습니다."),
    DECKS_READ_SUCCESS(HttpStatus.OK, "전체 컬럼 조회에 성공하였습니다."),
    DECK_READ_SUCCESS(HttpStatus.OK, "컬럼 조회에 성공하였습니다."),
    DECK_UPDATE_SUCCESS(HttpStatus.OK, "컬럼 수정에 성공하였습니다."),
    DECK_POSITION_UPDATE_SUCCESS(HttpStatus.OK, "컬럼 포지션 변경에 성공하였습니다."),
    DECK_DELETE_SUCCESS(HttpStatus.OK, "컬럼 삭제에 성공하였습니다."),

    // 카드
    CARD_CREATE_SUCCESS(HttpStatus.OK, "카드 생성에 성공하였습니다."),
    CARDS_READ_SUCCESS(HttpStatus.OK, "전체 카드 조회에 성공하였습니다."),
    CARDS_READ_BY_MEMBER_SUCCESS(HttpStatus.OK, "작업자별 카드 조회에 성공하였습니다."),
    CARDS_READ_BY_CARDSTATUS_SUCCESS(HttpStatus.OK, "상태별 카드 조회에 성공하였습니다."),
    CARD_READ_SUCCESS(HttpStatus.OK, "카드 조회에 성공하였습니다."),
    CARD_UPDATE_SUCCESS(HttpStatus.OK, "카드 수정에 성공하였습니다."),
    CARD_POSITION_UPDATE_SUCCESS(HttpStatus.OK, "카드 포지션 변경에 성공하였습니다."),
    CARD_DELETE_SUCCESS(HttpStatus.OK, "카드 삭제에 성공하였습니다."),

    // 댓글
    COMMENT_CREATE_SUCCESS(HttpStatus.OK, "댓글 생성에 성공하였습니다."),
    COMMENTS_READ_SUCCESS(HttpStatus.OK, "전체 댓글 조회에 성공하였습니다."),
    COMMENT_READ_SUCCESS(HttpStatus.OK, "댓글 조회에 성공하였습니다."),
    COMMENT_UPDATE_SUCCESS(HttpStatus.OK, "댓글 수정에 성공하였습니다."),
    COMMENT_DELETE_SUCCESS(HttpStatus.OK, "댓글 삭제에 성공하였습니다."),

    // 카드 상세
    CARDDETAIL_CREATE_SUCCESS(HttpStatus.OK, "카드 상세 생성에 성공하였습니다."),
    CARDDETAIL_READ_SUCCESS(HttpStatus.OK, "카드 상세 조회에 성공하였습니다."),
    CARDDETAIL_UPDATE_SUCCESS(HttpStatus.OK, "카드 상세 수정에 성공하였습니다."),
    CARDDETAIL_DELETE_SUCCESS(HttpStatus.OK, "카드 상세 삭제에 성공하였습니다."),

    // 카드 멤버
    CARD_INVITE_MEMBER_SUCCESS(HttpStatus.OK, "카드에 사용자 초대가 완료되었습니다."),
    CARD_MEMBER_READS_SUCCESS(HttpStatus.OK, "전체 카드 멤버 조회에 성공하였습니다."),
    CARD_MEMBER_DELETE_SUCCESS(HttpStatus.OK, "카드 멤버 삭제에 성공하였습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
