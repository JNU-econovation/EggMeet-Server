package com.fivepotato.eggmeetserver.domain.mentoring;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMenteeArea is a Querydsl query type for MenteeArea
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMenteeArea extends EntityPathBase<MenteeArea> {

    private static final long serialVersionUID = -1429879461L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMenteeArea menteeArea = new QMenteeArea("menteeArea");

    public final EnumPath<Category> category = createEnum("category", Category.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.fivepotato.eggmeetserver.domain.user.QUser mentee;

    public QMenteeArea(String variable) {
        this(MenteeArea.class, forVariable(variable), INITS);
    }

    public QMenteeArea(Path<? extends MenteeArea> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMenteeArea(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMenteeArea(PathMetadata metadata, PathInits inits) {
        this(MenteeArea.class, metadata, inits);
    }

    public QMenteeArea(Class<? extends MenteeArea> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.mentee = inits.isInitialized("mentee") ? new com.fivepotato.eggmeetserver.domain.user.QUser(forProperty("mentee"), inits.get("mentee")) : null;
    }

}

