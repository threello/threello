package com.sparta.threello.repository.boardMemeber;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.threello.entity.Board;
import com.sparta.threello.entity.BoardMember;
import com.sparta.threello.entity.QBoardMember;
import com.sparta.threello.enums.BoardMemberPermission;
import com.sparta.threello.enums.ErrorType;
import com.sparta.threello.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class BoardMemberRepositoryImpl implements BoardMemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public BoardMemberRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    //BoardMember에 userID로 조회하고, 조회된 보드중 permission이 Owner인것들을 조회
    @Override
    public List<BoardMember> findOwnerBoardsByUserId(Long userId) {
        QBoardMember boardMember = QBoardMember.boardMember;

        return queryFactory.selectFrom(boardMember)
                .where(boardMember.user.id.eq(userId)
                        .and(boardMember.permission.eq(BoardMemberPermission.OWNER)))
                .fetch();
    }

    @Override
    public List<BoardMember> findMemberBoardsByUserId(Long userId) {
        QBoardMember boardMember = QBoardMember.boardMember;

        List<BoardMember> boardMembers = queryFactory.selectFrom(boardMember)
                .where(boardMember.user.id.eq(userId)
                        .and(boardMember.permission.eq(BoardMemberPermission.MEMBER)))
                .fetch();
        return Optional.ofNullable(boardMembers).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_BOARD));
    }

    @Override
    public BoardMember findBoardMemberByBoardAndUserAndPermission(Long boardId, Long userId) {
        QBoardMember boardMember = QBoardMember.boardMember;

        BoardMember getBoardMember = queryFactory.selectFrom(boardMember)
                .where(boardMember.user.id.eq(userId)
                        .and(boardMember.board.id.eq(boardId)
                                .and(boardMember.permission.eq(BoardMemberPermission.OWNER)))
                )
                .fetchFirst();
        return Optional.ofNullable(getBoardMember).orElseThrow(() -> new CustomException(ErrorType.NOT_AVAILABLE_PERMISSION));
    }
}