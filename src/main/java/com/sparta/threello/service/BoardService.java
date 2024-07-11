package com.sparta.threello.service;

import com.sparta.threello.dto.BoardRequestDto;
import com.sparta.threello.dto.BoardResponseDto;
import com.sparta.threello.entity.Board;
import com.sparta.threello.entity.BoardMember;
import com.sparta.threello.entity.User;
import com.sparta.threello.enums.BoardMemberPermission;
import com.sparta.threello.enums.ErrorType;
import com.sparta.threello.enums.UserType;
import com.sparta.threello.exception.CustomException;
import com.sparta.threello.repository.board.BoardRepository;
import com.sparta.threello.repository.boardMemeber.BoardMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardMemberRepository boardMemberRepository;

    /**
     * [createBoard] 보드 생성
     * @param requestDto 요청 객체
     * @param loginUser 로그인한 회원 정보
     * @return responseDto
     **/
    public BoardResponseDto createBoard(BoardRequestDto requestDto, User loginUser){
        //[예외 1] - MANAGER 권한이 아닌 USER가 생성을 시도하는 경우
        checkManagerPermission(loginUser);

        Board board = new Board(requestDto);

        Board savedBoard = boardRepository.save(board);

        new BoardMember(loginUser, savedBoard, BoardMemberPermission.OWNER);

        return new BoardResponseDto(savedBoard);
    }


    /**
     * [getOwnerBoards] owner 타입 보드 불러오기
     * @param loginUser 로그인한 회원 정보
     * @return List<BoardResponseDto>
     **/
    public List<BoardResponseDto> getOwnerBoards(User loginUser) {
        //[QueryDSL] - BoardMember에 userID로 조회하고, 조회된 보드중 permission이 Owner인것들을 조회
        List<BoardMember> ownerBoardMembers = boardMemberRepository.findOwnerBoardsByUserId(loginUser.getId());

        //[예외 1] - 조회된 리스트가 없으면
        if (ownerBoardMembers.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_BOARD);
        }

        List<BoardResponseDto> ownerBoards = ownerBoardMembers.stream()
                .map(boardMember -> new BoardResponseDto(boardMember.getBoard()))
                .toList();

        return ownerBoards;
    }


    /**
     * [getMemberBoards] member 타입(초대된) 보드 불러오기
     * @param loginUser 로그인한 회원 정보
     * @return List<BoardResponseDto>
     **/
    public List<BoardResponseDto> getMemberBoards(User loginUser) {
        //[QueryDSL] - BoardMember에 userID로 조회하고, 조회된 보드중 permission이 Owner인것들을 조회
        List<BoardMember> memberBoardMembers = boardMemberRepository.findMemberBoardsByUserId(loginUser.getId());

        //[예외 1] - 조회된 리스트가 없으면
        if (memberBoardMembers.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_BOARD);
        }

        List<BoardResponseDto> memerBoards = memberBoardMembers.stream()
                .map(boardMember -> new BoardResponseDto(boardMember.getBoard()))
                .toList();

        return memerBoards;
    }


    /**
     * [updateBoard] 보드 수정하기
     * @param requestDto 수정할 내용
     * @param loginUser 로그인한 회원 정보
     * @return BoardResponseDto
     **/
    @Transactional
    public BoardResponseDto updateBoard(Long boardId, BoardRequestDto requestDto, User loginUser) {
        // [예외 1] - 존재하는 board인지 확인
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_BOARD));

        // [예외 2 QueryDSL] - 찾은 board과 user를 통해 boardMember를 조회하는데 Owner 권한을 가지고 있는지 확인
        boardMemberRepository.findBoardAndUserAndPermission(board.getId(), loginUser.getId());

        board.update(requestDto);

        return new BoardResponseDto(board);
    }



    //매니저 권한이 아니면 예외처리
    public void checkManagerPermission(User loginUser) {
        if (loginUser.getUserType().equals(UserType.USER)) {
            throw new CustomException(ErrorType.NOT_AVAILABLE_PERMISSION);
        }
    }

}
