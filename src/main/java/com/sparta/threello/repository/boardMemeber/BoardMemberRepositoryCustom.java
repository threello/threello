package com.sparta.threello.repository.boardMemeber;

import com.sparta.threello.entity.BoardMember;

import java.util.List;

public interface BoardMemberRepositoryCustom {
    List<BoardMember> findOwnerBoardsByUserId(Long userId);
}
