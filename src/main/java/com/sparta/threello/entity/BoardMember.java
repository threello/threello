package com.sparta.threello.entity;

import com.sparta.threello.enums.BoardMemberPermission;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.security.Permission;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "boardMember")
public class BoardMember extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //user와 join
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //board와 join
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    //보드의 생성자인지, 초대된 멤버인지 구분하기 위한 컬럼
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private BoardMemberPermission permission;

    public BoardMember(User user, Board board, BoardMemberPermission permission) {
        this.user = user;
        this.board = board;
        this.permission = permission;
    }
}