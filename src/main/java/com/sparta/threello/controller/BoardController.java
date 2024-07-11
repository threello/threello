package com.sparta.threello.controller;

import com.sparta.threello.dto.BoardRequestDto;
import com.sparta.threello.dto.BoardResponseDto;
import com.sparta.threello.dto.ResponseDataDto;
import com.sparta.threello.enums.ResponseStatus;
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
     * @param userDetails 회원 정보
     * @return status.code, message, responseDto
     **/
//    @PostMapping
//    public ResponseEntity<ResponseDataDto<BoardResponseDto>> createBoard(@Valid @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        BoardResponseDto responseDto = boardService.createBoard(requestDto, userDetails.getUser());
//        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.BOARD_CREATE_SUCCESS, responseDto));
//    }

    /**
     * [getOwnerBoards] owner 타입 보드 불러오기
     * @param userDetails 회원 정보
     * @return status.code, message, responseDto
     **/
//    @GetMapping("/owner")
//    public ResponseEntity<ResponseDataDto<List<BoardResponseDto>>> getOwnerBoards(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        List<BoardResponseDto> responseDtoList = boardService.getOwnerBoards(userDetails.getUser());
//        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.BOARDS_READ_SUCCESS, responseDtoList));
//    }

    /**
     * [getOwnerBoards] member 타입(초대된) 보드 불러오기
     * @param userDetails 회원 정보
     * @return status.code, message, responseDto
     **/
//    @GetMapping("/member")
//    public ResponseEntity<ResponseDataDto<List<BoardResponseDto>>> getMemberBoards(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        List<BoardResponseDto> responseDtoList = boardService.getMemberBoards(userDetails.getUser());
//        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.BOARDS_READ_SUCCESS, responseDtoList));
//    }

    /**
     * [updateBoard] 보드 수정하기
     * @param userDetails 회원 정보
     * @return status.code, message, responseDto
     **/
//    @PutMapping("/{boardId}")
//    public ResponseEntity<ResponseDataDto<BoardResponseDto>> updateBoard(@Valid @RequestBody BoardRequestDto requestDto, @PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        BoardResponseDto responseDto = boardService.updateBoard(boardId, requestDto, userDetails.getUser());
//        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.BOARD_UPDATE_SUCCESS, responseDto));
//    }
}
