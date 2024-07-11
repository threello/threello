package com.sparta.threello.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCardMember is a Querydsl query type for CardMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCardMember extends EntityPathBase<CardMember> {

    private static final long serialVersionUID = 695212830L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCardMember cardMember = new QCardMember("cardMember");

    public final QTimestamped _super = new QTimestamped(this);

    public final QCard card;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QUser user;

    public QCardMember(String variable) {
        this(CardMember.class, forVariable(variable), INITS);
    }

    public QCardMember(Path<? extends CardMember> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCardMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCardMember(PathMetadata metadata, PathInits inits) {
        this(CardMember.class, metadata, inits);
    }

    public QCardMember(Class<? extends CardMember> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.card = inits.isInitialized("card") ? new QCard(forProperty("card"), inits.get("card")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

