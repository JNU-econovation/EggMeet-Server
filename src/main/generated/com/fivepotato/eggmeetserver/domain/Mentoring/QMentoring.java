package com.fivepotato.eggmeetserver.domain.mentoring;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMentoring is a Querydsl query type for Mentoring
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMentoring extends EntityPathBase<Mentoring> {

    private static final long serialVersionUID = 1071914321L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMentoring mentoring = new QMentoring("mentoring");

    public final com.fivepotato.eggmeetserver.domain.chat.QChatroom chatroom;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isPending = createBoolean("isPending");

    public final ListPath<Meeting, QMeeting> meetings = this.<Meeting, QMeeting>createList("meetings", Meeting.class, QMeeting.class, PathInits.DIRECT2);

    public final com.fivepotato.eggmeetserver.domain.user.QUser mentee;

    public final com.fivepotato.eggmeetserver.domain.user.QUser mentor;

    public QMentoring(String variable) {
        this(Mentoring.class, forVariable(variable), INITS);
    }

    public QMentoring(Path<? extends Mentoring> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMentoring(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMentoring(PathMetadata metadata, PathInits inits) {
        this(Mentoring.class, metadata, inits);
    }

    public QMentoring(Class<? extends Mentoring> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatroom = inits.isInitialized("chatroom") ? new com.fivepotato.eggmeetserver.domain.chat.QChatroom(forProperty("chatroom"), inits.get("chatroom")) : null;
        this.mentee = inits.isInitialized("mentee") ? new com.fivepotato.eggmeetserver.domain.user.QUser(forProperty("mentee"), inits.get("mentee")) : null;
        this.mentor = inits.isInitialized("mentor") ? new com.fivepotato.eggmeetserver.domain.user.QUser(forProperty("mentor"), inits.get("mentor")) : null;
    }

}

