package com.sparta.threello.repository.boardMemeber;

import com.sparta.threello.entity.BoardMember;

import java.util.List;

public interface BoardMemberRepositoryCustom {
    //Owner권한을 가진 User의 Board
    List<BoardMember> findOwnerBoardsByUserId(Long userId);

    //Owner권한을 가진 특정 Board와 User
    BoardMember findBoardMemberByBoardAndUserAndPermission(Long boardId, Long userId);

    //Member권한을 가진 User의 Board
    List<BoardMember> findMemberBoardsByUserId(Long userId);
}
