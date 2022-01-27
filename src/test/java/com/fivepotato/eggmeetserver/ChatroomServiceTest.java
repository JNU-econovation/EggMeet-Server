package com.fivepotato.eggmeetserver;

import com.fivepotato.eggmeetserver.domain.chat.ChatroomQueryRepository;
import com.fivepotato.eggmeetserver.domain.chat.ChatroomRepository;
import com.fivepotato.eggmeetserver.domain.user.*;
import com.fivepotato.eggmeetserver.service.chat.ChatroomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ChatroomServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatroomRepository chatroomRepository;

    @Autowired
    private ChatroomService chatroomService;

    @Autowired
    private ChatroomQueryRepository chatroomQueryRepository;

    @BeforeAll
    void addTestUsers() {
        userRepository.deleteAll();
        chatroomRepository.deleteAll();

        userRepository.save(
                User.builder()
                        .nickname("user0")
                        .age(10)
                        .sex(Sex.MALE)
                        .location(Location.GWANGJU_BUKGU)
                        .description("user0Description")
                        .pictureIndex(0)
                        .offlineAvailable(true)
                        .onlineAvailable(true)
                        .loginType(LoginType.APPLE)
                        .email("user0@user0.com")
                        .encodedEmail("user0@user.com")
                        .role(Role.ROLE_USER)
                        .build()
        );

        userRepository.save(
                User.builder()
                        .nickname("user1")
                        .age(10)
                        .sex(Sex.MALE)
                        .location(Location.GWANGJU_BUKGU)
                        .description("user1Description")
                        .pictureIndex(0)
                        .offlineAvailable(true)
                        .onlineAvailable(true)
                        .loginType(LoginType.APPLE)
                        .email("user1@user1.com")
                        .encodedEmail("user1@user.com")
                        .role(Role.ROLE_USER)
                        .build()
        );

        userRepository.save(
                User.builder()
                        .nickname("user2")
                        .age(10)
                        .sex(Sex.MALE)
                        .location(Location.GWANGJU_BUKGU)
                        .description("user2Description")
                        .pictureIndex(0)
                        .offlineAvailable(true)
                        .onlineAvailable(true)
                        .loginType(LoginType.APPLE)
                        .email("user2@user2.com")
                        .encodedEmail("user2@user.com")
                        .role(Role.ROLE_USER)
                        .build()
        );
    }

    @Test
    void test_isParticipantByChatroomId() {
        long user0Id = userRepository.findByEmail("user0@user0.com").get().getId();
        long user1Id = userRepository.findByEmail("user1@user1.com").get().getId();
        long user2Id = userRepository.findByEmail("user2@user2.com").get().getId();

        chatroomService.createChatroom(user0Id, user1Id);
        chatroomService.createChatroom(user1Id, user2Id);

        long participatedChatroomId = chatroomService.getAllChatroom()
                .stream().filter(c -> c.getParticipantNicknames().contains("user0")).findAny()
                .get().getId();

        long notParticipatedChatroomId = chatroomService.getAllChatroom()
                .stream().filter(c -> !c.getParticipantNicknames().contains("user0")).findAny()
                .get().getId();

        Assertions.assertTrue(chatroomService.isParticipantByChatroomId(participatedChatroomId, user0Id));
        Assertions.assertFalse(chatroomService.isParticipantByChatroomId(notParticipatedChatroomId, user0Id));
    }
}
