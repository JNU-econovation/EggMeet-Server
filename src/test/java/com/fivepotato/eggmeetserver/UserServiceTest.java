package com.fivepotato.eggmeetserver;

import com.fivepotato.eggmeetserver.domain.Mentoring.*;
import com.fivepotato.eggmeetserver.domain.User.*;
import com.fivepotato.eggmeetserver.dto.User.UserProfileUpdateDto;
import com.fivepotato.eggmeetserver.service.User.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class UserServiceTest {

    @Value("${backdoor-token-secret}")
    private String BACKDOOR_TOKEN;

    @Autowired
    private UserService userService;

    @Autowired
    private MentorAreaRepository mentorAreaRepository;

    @Autowired
    private MenteeAreaRepository menteeAreaRepository;

    private User testUser;
    private MentorArea testMentorArea;
    private MenteeArea testMenteeArea;

    @BeforeAll
    void addTestUser() {
        testUser = User.builder()
                .nickname("user")
                .age(10)
                .sex(Sex.MALE)
                .location(Location.GWANGJU_BUKGU)
                .description("userDescription")
                .pictureIndex(0)
                .isOfflineAvailable(true)
                .isOnlineAvailable(true)
                .loginType(LoginType.APPLE)
                .email("user@user.com")
                .encodedEmail("user@user.com")
                .role(Role.ROLE_USER)
                .build();
        testMentorArea = MentorArea.builder()
                .mentor(testUser)
                .category(Category.PROGRAMMING_JAVA)
                .description("userDescription")
                .career("userCareer")
                .link("userLink")
                .growthPoint(1)
                .build();
        testMenteeArea = MenteeArea.builder()
                .mentee(testUser)
                .category(Category.PROGRAMMING_PYTHON)
                .description("userDescription")
                .build();

        userService.createUser(testUser);
        mentorAreaRepository.save(testMentorArea);
        menteeAreaRepository.save(testMenteeArea);
    }

    @Test
    @DisplayName("유저 정보 업데이트가 작동하는가?")
    void test_updateUserProfile_update_nicknameAndMentorCategoryMenteeCategory() {
        UserProfileUpdateDto userProfileUpdateDto = UserProfileUpdateDto.builder()
                .nickname("user999")
                .location(testUser.getLocation())
                .description(testUser.getDescription())
                .pictureIndex(testUser.getPictureIndex())
                .isOnlineAvailable(testUser.isOnlineAvailable())
                .isOfflineAvailable(testUser.isOfflineAvailable())
                .mentorCategory(Category.PROGRAMMING_PYTHON)
                .mentorDescription(testMentorArea.getDescription())
                .mentorCareer(testMentorArea.getCareer())
                .mentorLink(testMentorArea.getLink())
                .mentorGrowthPoint(testMentorArea.getGrowthPoint())
                .menteeCategory(Category.PROGRAMMING_JAVA)
                .menteeDescription(testMenteeArea.getDescription())
                .build();
        userService.updateUserProfile(1L, userProfileUpdateDto);

        User user = userService.getUserByUserId(1L);
        Assertions.assertEquals("user999", user.getNickname());
        Assertions.assertEquals(Category.PROGRAMMING_PYTHON, user.getMentorArea().getCategory());
        Assertions.assertEquals(Category.PROGRAMMING_JAVA, user.getMenteeArea().getCategory());
    }
}
