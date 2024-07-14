package com.sparta.threello.service;

import com.sparta.threello.dto.BoardRequestDto;
import com.sparta.threello.dto.BoardResponseDto;
import com.sparta.threello.dto.InviteRequestDto;
import com.sparta.threello.entity.Board;
import com.sparta.threello.entity.BoardMember;
import com.sparta.threello.entity.User;
import com.sparta.threello.enums.BoardMemberPermission;
import com.sparta.threello.enums.ErrorType;
import com.sparta.threello.enums.UserType;
import com.sparta.threello.exception.CustomException;
import com.sparta.threello.repository.UserRepository;
import com.sparta.threello.repository.board.BoardRepository;
import com.sparta.threello.repository.boardMemeber.BoardMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.sparta.threello.entity.QBoardMember.boardMember;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
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

        BoardMember boardMember = new BoardMember(loginUser, savedBoard, BoardMemberPermission.OWNER);
        boardMemberRepository.save(boardMember);

        return new BoardResponseDto(savedBoard);
    }


    /**
     * [getOwnerBoards] owner 타입 보드 불러오기
     * @param loginUser 로그인한 회원 정보
     * @return List<BoardResponseDto>
     **/
    public List<BoardResponseDto> getOwnerBoards(User loginUser) {
        if (loginUser.getUserType().equals(UserType.USER)) {
            throw new CustomException(ErrorType.NOT_AVAILABLE_PERMISSION);
        }

        //[QueryDSL] - BoardMember에 userID로 조회하고, 조회된 보드중 permission이 Owner인것들을 조회
        List<BoardMember> ownerBoardMembers = boardMemberRepository.findOwnerBoardsByUserId(loginUser.getId());


        //[예외 1] - 조회된 리스트가 없으면
        if (ownerBoardMembers.isEmpty()) {
            throw new CustomException(ErrorType.NOT_CREATE_BOARD);
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
        //[예외 1 QueryDSL] - BoardMember에 userID로 조회하고, 조회된 보드중 permission이 Owner인것들을 조회 없으면 예외처리
        List<BoardMember> boardMembers = boardMemberRepository.findMemberBoardsByUserId(loginUser.getId());

        //[예외 1] - 조회된 리스트가 없으면
        if (boardMembers.isEmpty()) {
            throw new CustomException(ErrorType.NOT_EXIST_BOARDS);
        }

        List<BoardResponseDto> boardMemberList = boardMembers.stream()
                .map(boardMember -> new BoardResponseDto(boardMember.getBoard()))
                .toList();

        return boardMemberList;
    }

    /**
     * [getBoard] 특정보드 찾기
     * @param boardId 보드 아이디
     * @param loginUser 로그인한 회원 정보
     * @return BoardResponseDto
     **/
    public BoardResponseDto getBoard(Long boardId, User loginUser) {
        // [예외 1] - 초대되지 않은 유저는 보드를 볼 수 없음
        if (boardMemberRepository.findByBoardIdAndUserId(boardId, loginUser.getId()).isEmpty()) {
            throw new CustomException(ErrorType.NOT_AVAILABLE_PERMISSION);
        }

        // [예외 1] - 존재하는 board인지 확인
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_BOARD));

        return new BoardResponseDto(board);
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

        // [예외 2 QueryDSL] - 찾은 board와 user를 통해 boardMember를 조회하는데 Owner 권한을 가지고 있는지 확인
        boardMemberRepository.findBoardMemberByBoardAndUserAndPermission(board.getId(), loginUser.getId());

        board.update(requestDto);

        return new BoardResponseDto(board);
    }

    /**
     * [deleteBoard] 보드 삭제하기
     * @param boardId 보드 아이디
     * @param loginUser 로그인한 회원 정보
     **/
    @Transactional
    public void deleteBoard(Long boardId, User loginUser) {
        // [예외 1] - 존재하는 board인지 확인
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_BOARD));

        // [예외 2 QueryDSL] - 찾은 board과 user를 통해 boardMember를 조회하는데 Owner 권한을 가지고 있는지 확인
        boardMemberRepository.findBoardMemberByBoardAndUserAndPermission(board.getId(), loginUser.getId());

        boardRepository.delete(board);
    }

    /**
     * [inviteBoardMember] 보드에 초대하기
     * @param boardId 보드 아이디
     * @param loginUser 로그인한 회원 정보
     * @param requestDto 초대할 회원 이메일
     **/
    @Transactional
    public void inviteBoardMember(Long boardId, InviteRequestDto requestDto, User loginUser) {
        // [예외 1] - 존재하는 board 인지 확인
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_BOARD));

        // [예외 2] - 보드의 OWNER가 아니면 초대 불가능
        if (!boardMemberRepository.findByBoardIdAndUserIdAndPermission(boardId, loginUser.getId(), BoardMemberPermission.OWNER).isPresent()) {
            throw new CustomException(ErrorType.NOT_AVAILABLE_PERMISSION);
        }

        // [예외 3] - 존재하는 email 인지 확인
        User inviteUser = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER));

        // [예외 4] - 자기 자신 초대 불가능
        if (loginUser.getId().equals(inviteUser.getId())) {
            throw new CustomException(ErrorType.CANNOT_INVITE_SELF);
        }

        // [예외 5] - 이미 초대된 사람은 초대 불가능
        if (boardMemberRepository.findByBoardIdAndUserId(boardId, inviteUser.getId()).isPresent()) {
            throw new CustomException(ErrorType.ALREADY_INVITED_USER);
        };

        BoardMember boardMember = new BoardMember(inviteUser, board, BoardMemberPermission.MEMBER);

        boardMemberRepository.save(boardMember);
    }


    //매니저 권한이 아니면 예외처리

    public void checkManagerPermission(User loginUser) {
        if (loginUser.getUserType().equals(UserType.USER)) {
            throw new CustomException(ErrorType.NOT_AVAILABLE_PERMISSION);
        }
    }
}
