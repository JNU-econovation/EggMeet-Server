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

    public static final QChatroom chatroom = new QChatroom("chatroom");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Message, QMessage> messages = this.<Message, QMessage>createList("messages", Message.class, QMessage.class, PathInits.DIRECT2);

    public final ListPath<com.fivepotato.eggmeetserver.domain.user.User, com.fivepotato.eggmeetserver.domain.user.QUser> participants = this.<com.fivepotato.eggmeetserver.domain.user.User, com.fivepotato.eggmeetserver.domain.user.QUser>createList("participants", com.fivepotato.eggmeetserver.domain.user.User.class, com.fivepotato.eggmeetserver.domain.user.QUser.class, PathInits.DIRECT2);

    public QChatroom(String variable) {
        super(Chatroom.class, forVariable(variable));
    }

    public QChatroom(Path<? extends Chatroom> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChatroom(PathMetadata metadata) {
        super(Chatroom.class, metadata);
    }

}

