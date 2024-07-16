package com.sparta.threello.repository.boardMemeber;

import com.sparta.threello.entity.Board;
import com.sparta.threello.entity.BoardMember;
import com.sparta.threello.entity.User;
import com.sparta.threello.enums.BoardMemberPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardMemberRepository extends JpaRepository<BoardMember, Long>, BoardMemberRepositoryCustom {
    Optional<Long> getBoardByUserId(Long id);

    Optional<BoardMember> findByBoardIdAndUserId(Long boardId, Long userId);

    Optional<BoardMember> findByBoardIdAndUserIdAndPermission(Long board_id, Long user_id, BoardMemberPermission permission);

    List<BoardMember> findBoardMemberByBoardId(Long boardId);
}
