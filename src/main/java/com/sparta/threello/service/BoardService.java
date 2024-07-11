package com.sparta.threello.service;

import com.sparta.threello.dto.BoardRequestDto;
import com.sparta.threello.dto.BoardResponseDto;
import com.sparta.threello.entity.Board;
import com.sparta.threello.entity.BoardMember;
import com.sparta.threello.entity.User;
import com.sparta.threello.enums.ErrorType;
import com.sparta.threello.enums.UserType;
import com.sparta.threello.exception.CustomException;
import com.sparta.threello.repository.board.BoardRepository;
import com.sparta.threello.repository.boardMemeber.BoardMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardMemberRepository boardMemberRepository;

    public BoardResponseDto createBoard(BoardRequestDto requestDto, User loginUser){
        //[예외 1] - MANAGER 권한이 아닌 USER가 생성을 시도하는 경우
        checkManagerPermission(loginUser);

        Board board = new Board(requestDto);

        boardRepository.save(board);

        BoardMember boardMember = new BoardMember(loginUser, board);

        return new BoardResponseDto(board);
    }

    public BoardResponseDto getBoards(User loginUser) {
        if (boardMemberRepository.getBoardByUserId(loginUser.getId()).isEmpty()) {
            throw new CustomException(ErrorType.NOT_AVAILABLE_PERMISSION);
        }
        return null;
    }


    //매니저 권한이 아니면 예외처리
    public void checkManagerPermission(User loginUser) {
        if (loginUser.getUserType().equals(UserType.USER)) {
            throw new CustomException(ErrorType.NOT_AVAILABLE_PERMISSION);
        }
    }

    //

}
