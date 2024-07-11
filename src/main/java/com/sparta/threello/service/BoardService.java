package com.sparta.threello.service;

import com.sparta.threello.dto.BoardRequestDto;
import com.sparta.threello.dto.BoardResponseDto;
import com.sparta.threello.entity.Board;
import com.sparta.threello.entity.User;
import com.sparta.threello.enums.ErrorType;
import com.sparta.threello.enums.UserType;
import com.sparta.threello.exception.CustomException;
import com.sparta.threello.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardResponseDto createBoard(BoardRequestDto requestDto, User loginUser){
        //[예외 1] - MANAGER 권한이 아닌 USER가 생성을 시도하는 경우
        checkManagerPermission(loginUser);

        Board board = new Board(requestDto);

        boardRepository.save(board);

        return new BoardResponseDto(board);
    }


    //매니저 권한이 아니면 예외처리
    public void checkManagerPermission(User loginUser) {
        if (loginUser.getUserType().equals(UserType.USER)) {
            throw new CustomException(ErrorType.NOT_AVAILABLE_PERMISSION);
        }
    }

    //

}
