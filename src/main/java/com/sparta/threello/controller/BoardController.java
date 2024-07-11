package com.sparta.threello.controller;

import com.sparta.threello.dto.BoardResponseDto;
import com.sparta.threello.dto.ResponseDataDto;
import com.sparta.threello.enums.ResponseStatus;
import com.sparta.threello.service.BoardService;
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
     * [getBoard] 보드 생성
     *
     * @param userDetails 회원 정보
     * @return status.code, message, responseDto
     **/
//    @GetMapping
//    public ResponseEntity<ResponseDataDto<List<BoardResponseDto>>> getBoards(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        List<BoardResponseDto> responseDtoList = boardService.getOwnerBoards(userDetails.getUser());
//        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.BOARDS_READ_SUCCESS, responseDtoList));
//    }
}
