package com.fivepotato.eggmeetserver.domain.Mentoring;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMentorArea is a Querydsl query type for MentorArea
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMentorArea extends EntityPathBase<MentorArea> {

    private static final long serialVersionUID = -934108866L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMentorArea mentorArea = new QMentorArea("mentorArea");

    public final StringPath career = createString("career");

    public final EnumPath<Category> category = createEnum("category", Category.class);

    public final StringPath description = createString("description");

    public final NumberPath<Byte> growthPoint = createNumber("growthPoint", Byte.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath link = createString("link");

    public final com.fivepotato.eggmeetserver.domain.User.QUser mentor;

    public QMentorArea(String variable) {
        this(MentorArea.class, forVariable(variable), INITS);
    }

    public QMentorArea(Path<? extends MentorArea> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMentorArea(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMentorArea(PathMetadata metadata, PathInits inits) {
        this(MentorArea.class, metadata, inits);
    }

    public QMentorArea(Class<? extends MentorArea> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.mentor = inits.isInitialized("mentor") ? new com.fivepotato.eggmeetserver.domain.User.QUser(forProperty("mentor"), inits.get("mentor")) : null;
    }

}

