package com.fivepotato.eggmeetserver.domain.chat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatroom is a Querydsl query type for Chatroom
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QChatroom extends EntityPathBase<Chatroom> {

    private static final long serialVersionUID = -1790706170L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatroom chatroom = new QChatroom("chatroom");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.fivepotato.eggmeetserver.domain.mentoring.QMentoring mentoring;

    public final ListPath<Message, QMessage> messages = this.<Message, QMessage>createList("messages", Message.class, QMessage.class, PathInits.DIRECT2);

    public final ListPath<com.fivepotato.eggmeetserver.domain.user.User, com.fivepotato.eggmeetserver.domain.user.QUser> participants = this.<com.fivepotato.eggmeetserver.domain.user.User, com.fivepotato.eggmeetserver.domain.user.QUser>createList("participants", com.fivepotato.eggmeetserver.domain.user.User.class, com.fivepotato.eggmeetserver.domain.user.QUser.class, PathInits.DIRECT2);

    public QChatroom(String variable) {
        this(Chatroom.class, forVariable(variable), INITS);
    }

    public QChatroom(Path<? extends Chatroom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatroom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatroom(PathMetadata metadata, PathInits inits) {
        this(Chatroom.class, metadata, inits);
    }

    public QChatroom(Class<? extends Chatroom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.mentoring = inits.isInitialized("mentoring") ? new com.fivepotato.eggmeetserver.domain.mentoring.QMentoring(forProperty("mentoring"), inits.get("mentoring")) : null;
    }

}

