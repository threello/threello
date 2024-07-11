package com.sparta.threello.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCard is a Querydsl query type for Card
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCard extends EntityPathBase<Card> {

    private static final long serialVersionUID = 455393124L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCard card = new QCard("card");

    public final QTimestamped _super = new QTimestamped(this);

    public final EnumPath<com.sparta.threello.enums.CardStatus> card_status = createEnum("card_status", com.sparta.threello.enums.CardStatus.class);

    public final StringPath cardDeckPosition = createString("cardDeckPosition");

    public final QCardDetail cardDetail;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QDeck deck;

    public final DateTimePath<java.time.LocalDateTime> dueAt = createDateTime("dueAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Long> position = createNumber("position", Long.class);

    public final StringPath title = createString("title");

    public QCard(String variable) {
        this(Card.class, forVariable(variable), INITS);
    }

    public QCard(Path<? extends Card> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCard(PathMetadata metadata, PathInits inits) {
        this(Card.class, metadata, inits);
    }

    public QCard(Class<? extends Card> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cardDetail = inits.isInitialized("cardDetail") ? new QCardDetail(forProperty("cardDetail"), inits.get("cardDetail")) : null;
        this.deck = inits.isInitialized("deck") ? new QDeck(forProperty("deck"), inits.get("deck")) : null;
    }

}

