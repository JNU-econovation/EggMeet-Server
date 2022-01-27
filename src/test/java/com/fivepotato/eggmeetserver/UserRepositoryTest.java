package com.fivepotato.eggmeetserver;

import com.fivepotato.eggmeetserver.domain.mentoring.*;
import com.fivepotato.eggmeetserver.domain.user.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MentorAreaRepository mentorAreaRepository;

    @Autowired
    private MenteeAreaRepository menteeAreaRepository;

    @BeforeAll
    void addTestUsers() {
        userRepository.deleteAll();

        User user0 = User.builder()
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
                .build();

        userRepository.save(user0);

        User user1 = User.builder()
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
                .encodedEmail("user1@user1.com")
                .role(Role.ROLE_USER)
                .build();
        MentorArea user1MentorArea = MentorArea.builder()
                .mentor(user1)
                .category(Category.IT_BACKEND)
                .description("user1Description")
                .career("user1Career")
                .link("user1Link")
                .growthCost(1)
                .build();

        userRepository.save(user1);
        mentorAreaRepository.save(user1MentorArea);

        User user2 = User.builder()
                .nickname("user2")
                .age(15)
                .sex(Sex.MALE)
                .location(Location.GWANGJU_BUKGU)
                .description("user2Description")
                .pictureIndex(0)
                .offlineAvailable(true)
                .onlineAvailable(true)
                .loginType(LoginType.APPLE)
                .email("user2@user2.com")
                .encodedEmail("user2@user2.com")
                .role(Role.ROLE_USER)
                .build();
        MentorArea user2MentorArea = MentorArea.builder()
                .mentor(user2)
                .category(Category.IT_APP)
                .description("user2Description")
                .career("user2Career")
                .link("user2Link")
                .growthCost(2)
                .build();


        userRepository.save(user2);
        mentorAreaRepository.save(user2MentorArea);

        User user3 = User.builder()
                .nickname("user3")
                .age(20)
                .sex(Sex.UNDEFINED)
                .location(Location.GWANGJU_SEOGU)
                .description("user3Description")
                .pictureIndex(0)
                .offlineAvailable(true)
                .onlineAvailable(false)
                .loginType(LoginType.APPLE)
                .email("user3@user3.com")
                .encodedEmail("user3@user3.com")
                .role(Role.ROLE_USER)
                .build();
        MenteeArea user3MenteeArea = MenteeArea.builder()
                .mentee(user3)
                .category(Category.IT_APP)
                .description("user3Description")
                .build();


        userRepository.save(user3);
        menteeAreaRepository.save(user3MenteeArea);

        User user4 = User.builder()
                .nickname("user4")
                .age(25)
                .sex(Sex.FEMALE)
                .location(Location.GWANGJU_SEOGU)
                .description("user4Description")
                .pictureIndex(0)
                .offlineAvailable(false)
                .onlineAvailable(false)
                .loginType(LoginType.APPLE)
                .email("user4@user4.com")
                .encodedEmail("user4@user4.com")
                .role(Role.ROLE_USER)
                .build();
        MenteeArea user4MenteeArea = MenteeArea.builder()
                .mentee(user4)
                .category(Category.IT_APP)
                .description("user4Description")
                .build();


        userRepository.save(user4);
        menteeAreaRepository.save(user4MenteeArea);
    }


    @Test
    @DisplayName("멘토들의 이름에 제대로 검색되는가?")
    void test_findUsersByMentorAreaIsNotNullAndNicknameContaining() {
        Pageable pageable = PageRequest.of(0, 10);
        String nickname = "user";

        List<String> names = userRepository.findUsersByMentorAreaIsNotNullAndNicknameContaining(pageable, nickname)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(names).containsExactly("user1", "user2");
    }

    @Test
    @DisplayName("멘티들의 이름에 제대로 검색되는가?")
    void test_findUsersByMenteeAreaIsNotNullAndNicknameContaining() {
        Pageable pageable = PageRequest.of(0, 10);
        String nickname = "user";

        List<String> names = userRepository.findUsersByMenteeAreaIsNotNullAndNicknameContaining(pageable, nickname)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(names).containsExactly("user3", "user4");
    }
}
