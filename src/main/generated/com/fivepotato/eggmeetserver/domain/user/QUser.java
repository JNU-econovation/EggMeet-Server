package com.fivepotato.eggmeetserver.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1790043509L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final StringPath description = createString("description");

    public final StringPath email = createString("email");

    public final StringPath encodedEmail = createString("encodedEmail");

    public final NumberPath<Integer> growthGrade = createNumber("growthGrade", Integer.class);

    public final NumberPath<Integer> growthPoint = createNumber("growthPoint", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isOfflineAvailable = createBoolean("isOfflineAvailable");

    public final BooleanPath isOnlineAvailable = createBoolean("isOnlineAvailable");

    public final EnumPath<Location> location = createEnum("location", Location.class);

    public final EnumPath<LoginType> loginType = createEnum("loginType", LoginType.class);

    public final com.fivepotato.eggmeetserver.domain.mentoring.QMenteeArea menteeArea;

    public final ListPath<com.fivepotato.eggmeetserver.domain.chat.Chatroom, com.fivepotato.eggmeetserver.domain.chat.QChatroom> menteeChatroom = this.<com.fivepotato.eggmeetserver.domain.chat.Chatroom, com.fivepotato.eggmeetserver.domain.chat.QChatroom>createList("menteeChatroom", com.fivepotato.eggmeetserver.domain.chat.Chatroom.class, com.fivepotato.eggmeetserver.domain.chat.QChatroom.class, PathInits.DIRECT2);

    public final ListPath<com.fivepotato.eggmeetserver.domain.mentoring.Mentoring, com.fivepotato.eggmeetserver.domain.mentoring.QMentoring> menteeMentoring = this.<com.fivepotato.eggmeetserver.domain.mentoring.Mentoring, com.fivepotato.eggmeetserver.domain.mentoring.QMentoring>createList("menteeMentoring", com.fivepotato.eggmeetserver.domain.mentoring.Mentoring.class, com.fivepotato.eggmeetserver.domain.mentoring.QMentoring.class, PathInits.DIRECT2);

    public final NumberPath<Float> menteeRating = createNumber("menteeRating", Float.class);

    public final com.fivepotato.eggmeetserver.domain.mentoring.QMentorArea mentorArea;

    public final ListPath<com.fivepotato.eggmeetserver.domain.chat.Chatroom, com.fivepotato.eggmeetserver.domain.chat.QChatroom> mentorChatroom = this.<com.fivepotato.eggmeetserver.domain.chat.Chatroom, com.fivepotato.eggmeetserver.domain.chat.QChatroom>createList("mentorChatroom", com.fivepotato.eggmeetserver.domain.chat.Chatroom.class, com.fivepotato.eggmeetserver.domain.chat.QChatroom.class, PathInits.DIRECT2);

    public final ListPath<com.fivepotato.eggmeetserver.domain.mentoring.Mentoring, com.fivepotato.eggmeetserver.domain.mentoring.QMentoring> mentorMentoring = this.<com.fivepotato.eggmeetserver.domain.mentoring.Mentoring, com.fivepotato.eggmeetserver.domain.mentoring.QMentoring>createList("mentorMentoring", com.fivepotato.eggmeetserver.domain.mentoring.Mentoring.class, com.fivepotato.eggmeetserver.domain.mentoring.QMentoring.class, PathInits.DIRECT2);

    public final NumberPath<Float> mentorRating = createNumber("mentorRating", Float.class);

    public final ListPath<com.fivepotato.eggmeetserver.domain.chat.Message, com.fivepotato.eggmeetserver.domain.chat.QMessage> messages = this.<com.fivepotato.eggmeetserver.domain.chat.Message, com.fivepotato.eggmeetserver.domain.chat.QMessage>createList("messages", com.fivepotato.eggmeetserver.domain.chat.Message.class, com.fivepotato.eggmeetserver.domain.chat.QMessage.class, PathInits.DIRECT2);

    public final StringPath nickname = createString("nickname");

    public final NumberPath<Integer> pictureIndex = createNumber("pictureIndex", Integer.class);

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final EnumPath<Sex> sex = createEnum("sex", Sex.class);

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.menteeArea = inits.isInitialized("menteeArea") ? new com.fivepotato.eggmeetserver.domain.mentoring.QMenteeArea(forProperty("menteeArea"), inits.get("menteeArea")) : null;
        this.mentorArea = inits.isInitialized("mentorArea") ? new com.fivepotato.eggmeetserver.domain.mentoring.QMentorArea(forProperty("mentorArea"), inits.get("mentorArea")) : null;
    }

}

