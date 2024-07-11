package com.sparta.threello.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeck is a Querydsl query type for Deck
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeck extends EntityPathBase<Deck> {

    private static final long serialVersionUID = 455426301L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDeck deck = new QDeck("deck");

    public final QTimestamped _super = new QTimestamped(this);

    public final QBoard board;

    public final ListPath<Card, QCard> cardList = this.<Card, QCard>createList("cardList", Card.class, QCard.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Long> position = createNumber("position", Long.class);

    public final StringPath title = createString("title");

    public QDeck(String variable) {
        this(Deck.class, forVariable(variable), INITS);
    }

    public QDeck(Path<? extends Deck> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDeck(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDeck(PathMetadata metadata, PathInits inits) {
        this(Deck.class, metadata, inits);
    }

    public QDeck(Class<? extends Deck> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board")) : null;
    }

}

