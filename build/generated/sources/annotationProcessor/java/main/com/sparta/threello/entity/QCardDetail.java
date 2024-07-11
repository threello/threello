package com.sparta.threello.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCardDetail is a Querydsl query type for CardDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCardDetail extends EntityPathBase<CardDetail> {

    private static final long serialVersionUID = 437758165L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCardDetail cardDetail = new QCardDetail("cardDetail");

    public final QCard card;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QCardDetail(String variable) {
        this(CardDetail.class, forVariable(variable), INITS);
    }

    public QCardDetail(Path<? extends CardDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCardDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCardDetail(PathMetadata metadata, PathInits inits) {
        this(CardDetail.class, metadata, inits);
    }

    public QCardDetail(Class<? extends CardDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.card = inits.isInitialized("card") ? new QCard(forProperty("card"), inits.get("card")) : null;
    }

}

