package com.fivepotato.eggmeetserver;

import com.fivepotato.eggmeetserver.domain.user.*;
import com.fivepotato.eggmeetserver.dto.chat.MessageSaveDto;
import com.fivepotato.eggmeetserver.service.chat.ChatroomService;
import com.fivepotato.eggmeetserver.service.chat.MessageService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
class ChatServiceTest {

    @Value("${backdoor-token-secret}")
    private String BACKDOOR_TOKEN;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatroomService chatroomService;

    @Autowired
    private MessageService messageService;

    @BeforeEach
    void addTestUsers() {
        User user0 = User.builder()
                .nickname("user0")
                .age(10)
                .sex(Sex.MALE)
                .location(Location.GWANGJU_BUKGU)
                .description("user0Description")
                .pictureIndex(0)
                .isOfflineAvailable(true)
                .isOnlineAvailable(true)
                .loginType(LoginType.APPLE)
                .email("user0@user0.com")
                .encodedEmail("user0@user.com")
                .role(Role.ROLE_USER)
                .build();

        User user1 = User.builder()
                .nickname("user1")
                .age(10)
                .sex(Sex.MALE)
                .location(Location.GWANGJU_BUKGU)
                .description("user1Description")
                .pictureIndex(0)
                .isOfflineAvailable(true)
                .isOnlineAvailable(true)
                .loginType(LoginType.APPLE)
                .email("user1@user1.com")
                .encodedEmail("user1@user.com")
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user0);
        userRepository.save(user1);
    }

    @Test
    @DisplayName("유저가 삭제되면, 정상적으로 작성한 메세지가 삭제되는지?")
    void test_MessageOrphanRemoval_onUserDeletion() {
        chatroomService.createChatroom(1L, 2L);
        messageService.createMessage(1L, 1L, new MessageSaveDto("yeah"));

        userRepository.deleteById(1L);

        Assertions.assertEquals(0, messageService.getMessagesByChatroomId(1L).size());
    }

    @Test
    @DisplayName("유저가 삭제되면, 정상적으로 작성한 채팅방 목록에서 삭제되는지?")
    void test_ChatroomOrphanRemoval_onUserDeletion() {
        chatroomService.createChatroom(1L, 2L);

        userRepository.deleteById(1L);

        Assertions.assertEquals(1, chatroomService.findChatroomByRoomId(1L).getParticipants().size());
    }
}
