package com.sparta.threello.repository.boardMemeber;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.threello.entity.BoardMember;
import com.sparta.threello.entity.QBoardMember;
import com.sparta.threello.enums.BoardMemberPermission;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

        return queryFactory.selectFrom(boardMember)
                .where(boardMember.user.id.eq(userId)
                        .and(boardMember.permission.eq(BoardMemberPermission.MEMBER)))
                .fetch();
    }
}