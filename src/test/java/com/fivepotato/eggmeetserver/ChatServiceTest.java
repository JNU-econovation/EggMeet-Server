package com.fivepotato.eggmeetserver;

import com.fivepotato.eggmeetserver.domain.chat.ChatroomRepository;
import com.fivepotato.eggmeetserver.domain.chat.MessageRepository;
import com.fivepotato.eggmeetserver.domain.user.*;
import com.fivepotato.eggmeetserver.dto.chat.PersonalMessageSaveDto;
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

        user0 = userService.getUserByEmail("user0@user0.com");
        user1 = userService.getUserByEmail("user1@user1.com");
    }

    @Test
    @DisplayName("????????? ????????????, ??????????????? ????????? ???????????? ????????????????")
    void test_MessageOrphanRemoval_onUserDeletion() {
        Long chatroomId = chatroomService.createChatroom(user0.getId(), user1.getId()).getId();
//        messageService.createPersonalMessage(chatroomId, user0.getId(), new PersonalMessageSaveDto("yeah"));

        userRepository.delete(user0);

        Assertions.assertEquals(0, messageService.getMessagesByChatroomId(chatroomId).size());
    }

    @Test
    @DisplayName("????????? ????????????, ??????????????? ????????? ????????? ???????????? ????????????????")
    void test_ChatroomOrphanRemoval_onUserDeletion() {
        Long chatroomId = chatroomService.createChatroom(user0.getId(), user1.getId()).getId();

        userRepository.delete(user0);

        Assertions.assertNull(chatroomService.getChatroomByRoomId(chatroomId).getMentee());
        Assertions.assertNotNull(chatroomService.getChatroomByRoomId(chatroomId).getMentor());
    }

    @Test
    @DisplayName("????????? ????????? ??????????????? ??????????????????? (ManyToMany Relation between chatroom and user")
    void test_chatroomRemoval_ManyToManyRelationBetweenChatroomAndUser() {
        Long chatroomId = chatroomService.createChatroom(user0.getId(), user1.getId()).getId();
        chatroomService.deleteChatroomByChatroomId(chatroomId);

        // ????????? ????????? DB??? ???????????????????
        Assertions.assertEquals(0, chatroomService.getAllChatroom().size());

        // ????????? ????????? ?????? ???????????? ???????????? ?????? ?????????????
//        Assertions.assertEquals(0, user0.getChatrooms().size());

        // ????????? ????????? ?????? ??????????????? ????????? ????????? ??????????
        Assertions.assertTrue(userRepository.existsByNickname("user0"));
        Assertions.assertTrue(userRepository.existsByNickname("user1"));
    }
}
