package com.sparta.threello.repository.boardMemeber;

import com.sparta.threello.entity.Board;
import com.sparta.threello.entity.BoardMember;

import java.util.List;

public interface BoardMemberRepositoryCustom {
    List<BoardMember> findOwnerBoardsByUserId(Long userId);

    BoardMember findBoardAndUserAndPermission(Long boardId, Long userId);

    List<BoardMember> findMemberBoardsByUserId(Long userId);
}
