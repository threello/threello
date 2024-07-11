package com.sparta.threello.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 455946271L;

    public static final QUser user = new QUser("user");

    public final QTimestamped _super = new QTimestamped(this);

    public final StringPath accountId = createString("accountId");

    public final ListPath<BoardMember, QBoardMember> boardMemberList = this.<BoardMember, QBoardMember>createList("boardMemberList", BoardMember.class, QBoardMember.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath refreshToken = createString("refreshToken");

    public final EnumPath<com.sparta.threello.enums.UserStatus> userStatus = createEnum("userStatus", com.sparta.threello.enums.UserStatus.class);

    public final EnumPath<com.sparta.threello.enums.UserType> userType = createEnum("userType", com.sparta.threello.enums.UserType.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

