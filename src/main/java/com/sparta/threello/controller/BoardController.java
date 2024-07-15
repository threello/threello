package com.sparta.threello.controller;

import com.sparta.threello.dto.*;
import com.sparta.threello.enums.ResponseStatus;
import com.sparta.threello.security.UserDetailsImpl;
import com.sparta.threello.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    /**
     * [createBoard] 보드 생성
     * @param requestDto 요청 객체
     * @param userDetails 회원 정보xDSA
     * @return status.code, message, responseDto
     **/
    @PostMapping
    public ResponseEntity<ResponseDataDto<BoardResponseDto>> createBoard(
            @Valid @RequestBody BoardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BoardResponseDto responseDto = boardService.createBoard(requestDto, userDetails.getUser());
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.BOARD_CREATE_SUCCESS, responseDto));
    }

    /**
     * [getOwnerBoards] owner 타입 보드 불러오기
     * @param userDetails 회원 정보
     * @return status.code, message, responseDto
     **/
    @GetMapping("/owner")
    public ResponseEntity<ResponseDataDto<List<BoardResponseDto>>> getOwnerBoards(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<BoardResponseDto> responseDtoList = boardService.getOwnerBoards(userDetails.getUser());
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.BOARDS_READ_SUCCESS, responseDtoList));
    }

    /**
     * [getOwnerBoards] member 타입(초대된) 보드 불러오기
     * @param userDetails 회원 정보
     * @return status.code, message, responseDto
     **/
    @GetMapping("/member")
    public ResponseEntity<ResponseDataDto<List<BoardResponseDto>>> getMemberBoards(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<BoardResponseDto> responseDtoList = boardService.getMemberBoards(userDetails.getUser());
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.BOARDS_READ_SUCCESS, responseDtoList));
    }

    /**
     * [getBoard] 특정 보드 조회하기
     * @param userDetails 회원 정보
     * @return status.code, message, responseDto
     **/
    @GetMapping("/{boardId}")
    public ResponseEntity<ResponseDataDto<BoardResponseDto>> getBoard(
            @PathVariable Long boardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BoardResponseDto responseDto = boardService.getBoard(boardId, userDetails.getUser());
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.BOARD_READ_SUCCESS, responseDto));
    }

    /**
     * [updateBoard] 보드 수정하기
     * @param userDetails 회원 정보
     * @return status.code, message, responseDto
     **/
    @PutMapping("/{boardId}")
    public ResponseEntity<ResponseDataDto<BoardResponseDto>> updateBoard(
            @Valid @RequestBody BoardRequestDto requestDto,
            @PathVariable Long boardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BoardResponseDto responseDto = boardService.updateBoard(boardId, requestDto, userDetails.getUser());
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.BOARD_UPDATE_SUCCESS, responseDto));
    }

    /**
     * [deleteBoard] 보드 삭제하기
     * @param userDetails 회원 정보
     * @param boardId 보드 아이디
     * @return status.code, message
     **/
    @DeleteMapping("/{boardId}")
    public ResponseEntity<ResponseMessageDto> deleteBoard(
            @PathVariable Long boardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardService.deleteBoard(boardId, userDetails.getUser());
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.BOARD_DELETE_SUCCESS));
    }

    /**
     * [inviteBoardMember] 보드 초대하기
     * @param userDetails 회원 정보
     * @param boardId     보드 아이디
     * @return status.code, message
     **/
    @PostMapping("/{boardId}/invite")
    public ResponseEntity<ResponseMessageDto> inviteBoardMember(
            @Valid @RequestBody InviteRequestDto requestDto,
            @PathVariable Long boardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardService.inviteBoardMember(boardId, requestDto, userDetails.getUser());
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.BOARD_INVITE_MEMBER_SUCCESS));
    }
}
