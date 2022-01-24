package com.fivepotato.eggmeetserver;

import com.fivepotato.eggmeetserver.domain.chat.ChatroomRepository;
import com.fivepotato.eggmeetserver.domain.chat.MessageRepository;
import com.fivepotato.eggmeetserver.domain.user.*;
import com.fivepotato.eggmeetserver.dto.chat.MessageSaveDto;
import com.fivepotato.eggmeetserver.service.chat.ChatroomService;
import com.fivepotato.eggmeetserver.service.chat.MessageService;
import com.fivepotato.eggmeetserver.service.user.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatroomRepository chatroomRepository;

    @Autowired
    private ChatroomService chatroomService;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageService messageService;

    private User user0;
    private User user1;

    @BeforeEach
    void addTestUsers() {
        userRepository.deleteAll();
        chatroomRepository.deleteAll();
        messageRepository.deleteAll();

        userRepository.save(
                User.builder()
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
                        .isOfflineAvailable(true)
                        .isOnlineAvailable(true)
                        .loginType(LoginType.APPLE)
                        .email("user1@user1.com")
                        .encodedEmail("user1@user.com")
                        .role(Role.ROLE_USER)
                        .build()
        );

        user0 = userService.getUserByEmail("user0@user0.com");
        user1 = userService.getUserByEmail("user1@user1.com");
    }

    @Test
    @DisplayName("유저가 삭제되면, 정상적으로 작성한 메세지가 삭제되는지?")
    void test_MessageOrphanRemoval_onUserDeletion() {
        Long chatroomId = chatroomService.createChatroom(user0.getId(), user1.getId()).getId();
        messageService.createMessage(chatroomId, user0.getId(), new MessageSaveDto("yeah"));

        userRepository.delete(user0);

        Assertions.assertEquals(0, messageService.getMessagesByChatroomId(chatroomId).size());
    }

    @Test
    @DisplayName("유저가 삭제되면, 정상적으로 작성한 채팅방 목록에서 삭제되는지?")
    void test_ChatroomOrphanRemoval_onUserDeletion() {
        Long chatroomId = chatroomService.createChatroom(user0.getId(), user1.getId()).getId();

        userRepository.delete(user0);

        Assertions.assertEquals(1, chatroomService.getChatroomByRoomId(chatroomId).getParticipants().size());
    }

    @Test
    @DisplayName("채팅방 삭제가 정상적으로 이루어지는지? (ManyToMany Relation between chatroom and user")
    void test_chatroomRemoval_ManyToManyRelationBetweenChatroomAndUser() {
        Long chatroomId = chatroomService.createChatroom(user0.getId(), user1.getId()).getId();
        chatroomService.deleteChatroomByChatroomId(chatroomId);

        // 채팅방 삭제가 DB에 반영되었는지?
        Assertions.assertEquals(0, chatroomService.getAllChatroom().size());

        // 채팅방 삭제가 유저 엔티티의 컬럼에도 반영 되었는지?
//        Assertions.assertEquals(0, user0.getChatrooms().size());

        // 채팅방 삭제가 유저 엔티티에게 영향을 미치지 않은지?
        Assertions.assertTrue(userRepository.existsByNickname("user0"));
        Assertions.assertTrue(userRepository.existsByNickname("user1"));
    }
}
