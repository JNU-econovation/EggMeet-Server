package com.fivepotato.eggmeetserver.domain.chat;

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

    private BooleanExpression containsUserId(Long userId) {
        return QChatroom.chatroom.participants.any().id.eq(userId);
    }
}
