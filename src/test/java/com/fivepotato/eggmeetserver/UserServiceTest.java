package com.fivepotato.eggmeetserver;

import com.fivepotato.eggmeetserver.domain.mentoring.*;
import com.fivepotato.eggmeetserver.domain.user.*;
import com.fivepotato.eggmeetserver.domain.mentoring.MenteeAreaRepository;
import com.fivepotato.eggmeetserver.domain.mentoring.MentorAreaRepository;
import com.fivepotato.eggmeetserver.dto.user.UserProfileUpdateDto;
import com.fivepotato.eggmeetserver.service.user.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MentorAreaRepository mentorAreaRepository;

    @Autowired
    private MenteeAreaRepository menteeAreaRepository;


    @BeforeAll
    void addTestUser() {
        userRepository.deleteAll();

        User user = User.builder()
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
        MentorArea mentorArea = MentorArea.builder()
                .mentor(user)
                .category(Category.IT_BACKEND)
                .description("userDescription")
                .career("userCareer")
                .link("userLink")
                .growthCost(1)
                .build();
        MenteeArea menteeArea = MenteeArea.builder()
                .mentee(user)
                .category(Category.IT_WEB)
                .description("userDescription")
                .build();

        userService.createUser(user);
        mentorAreaRepository.save(mentorArea);
        menteeAreaRepository.save(menteeArea);
    }

    @Test
    @DisplayName("유저 정보 업데이트가 작동하는가?")
    void test_updateUserProfile_update_nicknameAndMentorCategoryMenteeCategory() {
        User originUser = userService.getUserByEmail("user@user.com");

        UserProfileUpdateDto userProfileUpdateDto = UserProfileUpdateDto.builder()
                .nickname("user999")
                .location(originUser.getLocation())
                .description(originUser.getDescription())
                .pictureIndex(originUser.getPictureIndex())
                .isOnlineAvailable(originUser.isOnlineAvailable())
                .isOfflineAvailable(originUser.isOfflineAvailable())
                .mentorCategory(Category.IT_WEB)
                .mentorDescription(originUser.getMentorArea().getDescription())
                .mentorCareer(originUser.getMentorArea().getCareer())
                .mentorLink(originUser.getMentorArea().getLink())
                .mentorGrowthCost(originUser.getMentorArea().getGrowthCost())
                .menteeCategory(Category.IT_BACKEND)
                .menteeDescription(originUser.getMenteeArea().getDescription())
                .build();
        userService.updateUserProfile(originUser.getId(), userProfileUpdateDto);

        User updatedUser = userService.getUserByUserId(originUser.getId());
        Assertions.assertEquals(userProfileUpdateDto.getNickname(), updatedUser.getNickname());
        Assertions.assertEquals(userProfileUpdateDto.getMentorCategory(), updatedUser.getMentorArea().getCategory());
        Assertions.assertEquals(userProfileUpdateDto.getMenteeCategory(), updatedUser.getMenteeArea().getCategory());
    }
}
