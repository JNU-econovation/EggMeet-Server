package com.fivepotato.eggmeetserver.domain.chat;

import com.fivepotato.eggmeetserver.domain.user.QUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatroomQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public ChatroomQueryRepository(JPAQueryFactory jpaQueryFactory) {
        super(Chatroom.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Chatroom> findAllByParticipantsContainsUserId(Long userId) {
        return jpaQueryFactory
                .selectFrom(QChatroom.chatroom)
                .where(
                        containsUserId(userId)
                )
                .fetch();
    }

    public Boolean isParticipantByChatroomId(long chatroomId, long userId) {
        Chatroom chatroom = jpaQueryFactory
                .selectFrom(QChatroom.chatroom)
                .where(
                        eqChatroomId(chatroomId),
                        containsUserId(userId)
                )
                .fetchFirst();

        if (chatroom == null) {
            return false;
        } else {
            return true;
        }
    }

    private BooleanBuilder containsUserId(long userId) {
        BooleanBuilder userBuilder = new BooleanBuilder();
        userBuilder.or(QChatroom.chatroom.mentee.id.eq(userId));
        userBuilder.or(QChatroom.chatroom.mentor.id.eq(userId));

        return userBuilder;
    }

    private BooleanExpression eqChatroomId(long chatroomId) {
        return QChatroom.chatroom.id.eq(chatroomId);
    }
}
