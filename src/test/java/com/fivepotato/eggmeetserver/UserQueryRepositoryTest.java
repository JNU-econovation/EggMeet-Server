package com.fivepotato.eggmeetserver;

import com.fivepotato.eggmeetserver.domain.Mentoring.*;
import com.fivepotato.eggmeetserver.domain.User.*;
import com.fivepotato.eggmeetserver.dto.Mentoring.SortOrder;
import com.fivepotato.eggmeetserver.service.User.UserService;
import org.junit.jupiter.api.*;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class UserQueryRepositoryTest {

    @Value("${backdoor-token-secret}")
    private String BACKDOOR_TOKEN;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MentorAreaRepository mentorAreaRepository;

    @Autowired
    private MenteeAreaRepository menteeAreaRepository;

    @Autowired
    private UserQueryRepository userQueryRepository;

    @BeforeAll
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

        userRepository.save(user0);

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
                .encodedEmail("user1@user1.com")
                .role(Role.ROLE_USER)
                .build();
        MentorArea user1MentorArea = MentorArea.builder()
                .mentor(user1)
                .category(Category.PROGRAMMING_JAVA)
                .description("user1Description")
                .career("user1Career")
                .link("user1Link")
                .growthPoint(1)
                .build();
        MenteeArea user1MenteeArea = MenteeArea.builder()
                .mentee(user1)
                .category(Category.PROGRAMMING_PYTHON)
                .description("user1Description")
                .build();

        userRepository.save(user1);
        mentorAreaRepository.save(user1MentorArea);
        menteeAreaRepository.save(user1MenteeArea);

        User user2 = User.builder()
                .nickname("user2")
                .age(10)
                .sex(Sex.MALE)
                .location(Location.GWANGJU_BUKGU)
                .description("user2Description")
                .pictureIndex(0)
                .isOfflineAvailable(true)
                .isOnlineAvailable(true)
                .loginType(LoginType.APPLE)
                .email("user2@user2.com")
                .encodedEmail("user2@user2.com")
                .role(Role.ROLE_USER)
                .build();
        MentorArea user2MentorArea = MentorArea.builder()
                .mentor(user2)
                .category(Category.PROGRAMMING_C)
                .description("user2Description")
                .career("user2Career")
                .link("user2Link")
                .growthPoint(2)
                .build();
        MenteeArea user2MenteeArea = MenteeArea.builder()
                .mentee(user2)
                .category(Category.PROGRAMMING_PYTHON)
                .description("user2Description")
                .build();


        userRepository.save(user2);
        mentorAreaRepository.save(user2MentorArea);
        menteeAreaRepository.save(user2MenteeArea);

        User user3 = User.builder()
                .nickname("user3")
                .age(10)
                .sex(Sex.MALE)
                .location(Location.GWANGJU_SEOGU)
                .description("user3Description")
                .pictureIndex(0)
                .isOfflineAvailable(true)
                .isOnlineAvailable(true)
                .loginType(LoginType.APPLE)
                .email("user3@user3.com")
                .encodedEmail("user3@user3.com")
                .role(Role.ROLE_USER)
                .build();
        MentorArea user3MentorArea = MentorArea.builder()
                .mentor(user3)
                .category(Category.PROGRAMMING_PYTHON)
                .description("user3Description")
                .career("user3Career")
                .link("user3Link")
                .growthPoint(3)
                .build();
        MenteeArea user3MenteeArea = MenteeArea.builder()
                .mentee(user3)
                .category(Category.PROGRAMMING_C)
                .description("user3Description")
                .build();


        userRepository.save(user3);
        mentorAreaRepository.save(user3MentorArea);
        menteeAreaRepository.save(user3MenteeArea);

        User user4 = User.builder()
                .nickname("user4")
                .age(10)
                .sex(Sex.MALE)
                .location(Location.GWANGJU_SEOGU)
                .description("user4Description")
                .pictureIndex(0)
                .isOfflineAvailable(true)
                .isOnlineAvailable(true)
                .loginType(LoginType.APPLE)
                .email("user4@user4.com")
                .encodedEmail("user4@user4.com")
                .role(Role.ROLE_USER)
                .build();
        MentorArea user4MentorArea = MentorArea.builder()
                .mentor(user4)
                .category(Category.PROGRAMMING_PYTHON)
                .description("user4Description")
                .career("user4Career")
                .link("user4Link")
                .growthPoint(4)
                .build();
        MenteeArea user4MenteeArea = MenteeArea.builder()
                .mentee(user4)
                .category(Category.PROGRAMMING_C)
                .description("user4Description")
                .build();


        userRepository.save(user4);
        mentorAreaRepository.save(user4MentorArea);
        menteeAreaRepository.save(user4MenteeArea);

        User user5 = User.builder()
                .nickname("user5")
                .age(10)
                .sex(Sex.MALE)
                .location(Location.GWANGJU_BUKGU)
                .description("user5Description")
                .pictureIndex(0)
                .isOfflineAvailable(true)
                .isOnlineAvailable(true)
                .loginType(LoginType.APPLE)
                .email("user5@user5.com")
                .encodedEmail("user5@user5.com")
                .role(Role.ROLE_USER)
                .build();
        MentorArea user5MentorArea = MentorArea.builder()
                .mentor(user5)
                .category(Category.PROGRAMMING_C)
                .description("user5Description")
                .career("user5Career")
                .link("user5Link")
                .growthPoint(0)
                .build();
        MenteeArea user5MenteeArea = MenteeArea.builder()
                .mentee(user5)
                .category(Category.PROGRAMMING_JAVA)
                .description("user5Description")
                .build();


        userRepository.save(user5);
        mentorAreaRepository.save(user5MentorArea);
        menteeAreaRepository.save(user5MenteeArea);
    }


    @Test
    @DisplayName("멘토를 location 조건에 맞게 불러오는가?")
    void test_findMentorDtosByMultipleConditionsOnPageable_eqLocation() {
        Pageable pageable = PageRequest.of(0, 10);
        Location location = Location.GWANGJU_BUKGU;
        Category category = null;
        SortOrder mentorRatingSortOrder = null;
        SortOrder growthPointSortOrder = null;

        List<String> names = userQueryRepository.findMentorsByMultipleConditionsOnPageable(pageable, location, category, mentorRatingSortOrder, growthPointSortOrder)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(names).containsExactly("user1", "user2", "user5");
    }

    @Test
    @DisplayName("멘토를 category 조건에 맞게 불러오는가?")
    void test_findMentorDtosByMultipleConditionsOnPageable_eqCategory() {
        Pageable pageable = PageRequest.of(0, 10);
        Location location = null;
        Category category = Category.PROGRAMMING_PYTHON;
        SortOrder mentorRatingSortOrder = null;
        SortOrder growthPointSortOrder = null;

        List<String> names = userQueryRepository.findMentorsByMultipleConditionsOnPageable(pageable, location, category, mentorRatingSortOrder, growthPointSortOrder)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(names).containsExactly("user3", "user4");
    }

    @Test
    @DisplayName("멘토를 location + category 조건에 맞게 불러오는가?")
    void test_findMentorDtosByMultipleConditionsOnPageable_eqLocationAndEqCategory() {
        Pageable pageable = PageRequest.of(0, 10);
        Location location = Location.GWANGJU_BUKGU;
        Category category = Category.PROGRAMMING_C;
        SortOrder mentorRatingSortOrder = null;
        SortOrder growthPointSortOrder = null;

        List<String> names = userQueryRepository.findMentorsByMultipleConditionsOnPageable(pageable, location, category, mentorRatingSortOrder, growthPointSortOrder)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(names).containsExactly("user2", "user5");
    }

    @Test
    @DisplayName("멘토를 location + category 조건에 맞게 growthPoint 오름차순에 정렬해서 불러오는가?")
    void test_findMentorDtosByMultipleConditionsOnPageable_eqLocationAndEqCategory_orderByGrowthPointAsc() {
        Pageable pageable = PageRequest.of(0, 10);
        Location location = Location.GWANGJU_BUKGU;
        Category category = Category.PROGRAMMING_C;
        SortOrder mentorRatingSortOrder = null;
        SortOrder growthPointSortOrder = SortOrder.ASCENDING;

        List<String> names = userQueryRepository.findMentorsByMultipleConditionsOnPageable(pageable, location, category, mentorRatingSortOrder, growthPointSortOrder)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(names).containsExactly("user5", "user2");
    }

    @Test
    @DisplayName("멘토를 growthPoint 오름차순 정렬해서 불러오는가?")
    void test_findMentorDtosByMultipleConditionsOnPageable_orderByGrowthPointAsc() {
        Pageable pageable = PageRequest.of(0, 10);
        Location location = null;
        Category category = null;
        SortOrder mentorRatingSortOrder = null;
        SortOrder growthPointSortOrder = SortOrder.ASCENDING;

        List<String> names = userQueryRepository.findMentorsByMultipleConditionsOnPageable(pageable, location, category, mentorRatingSortOrder, growthPointSortOrder)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(names).containsExactly("user5", "user1", "user2", "user3", "user4");
    }

    @Test
    @DisplayName("멘토를 growthPoint 내림차순 정렬해서 불러오는가?")
    void test_findMentorDtosByMultipleConditionsOnPageable_orderByGrowthPointDesc() {
        Pageable pageable = PageRequest.of(0, 10);
        Location location = null;
        Category category = null;
        SortOrder mentorRatingSortOrder = null;
        SortOrder growthPointSortOrder = SortOrder.DESCENDING;

        List<String> names = userQueryRepository.findMentorsByMultipleConditionsOnPageable(pageable, location, category, mentorRatingSortOrder, growthPointSortOrder)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(names).containsExactly("user4", "user3", "user2", "user1", "user5");
    }

    @Test
    @DisplayName("멘토를 올바르게 페이징하여 불러오는가")
    void test_findMentorDtosByMultipleConditionsOnPageable_OnPageable() {
        Pageable firstPageable = PageRequest.of(0, 1);
        Pageable secondPageable = PageRequest.of(1, 1);
        Location location = null;
        Category category = null;
        SortOrder mentorRatingSortOrder = null;
        SortOrder growthPointSortOrder = null;

        List<String> firstNames = userQueryRepository.findMentorsByMultipleConditionsOnPageable(firstPageable, location, category, mentorRatingSortOrder, growthPointSortOrder)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(firstNames.size()).isEqualTo(1);

        List<String> secondNames = userQueryRepository.findMentorsByMultipleConditionsOnPageable(secondPageable, location, category, mentorRatingSortOrder, growthPointSortOrder)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(secondNames.size()).isEqualTo(1);

        Assertions.assertThat(firstNames).isNotEqualTo(secondNames);
    }

    @Test
    @DisplayName("멘티를 location 조건에 맞게 불러오는가")
    void test_findMenteeDtosByMultipleConditionsOnPageable_eqLocation() {
        Pageable pageable = PageRequest.of(0, 10);
        Location location = Location.GWANGJU_BUKGU;
        Category category = null;
        SortOrder menteeRatingSortOrder = null;

        List<String> names = userQueryRepository.findMenteesByMultipleConditionsOnPageable(pageable, location, category, menteeRatingSortOrder)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(names).containsExactly("user1", "user2", "user5");
    }

    @Test
    @DisplayName("멘티를 category 조건에 맞게 불러오는가?")
    void test_findMenteeDtosByMultipleConditionsOnPageable_eqMenteeCategory() {
        Pageable pageable = PageRequest.of(0, 10);
        Location location = null;
        Category category = Category.PROGRAMMING_PYTHON;
        SortOrder menteeRatingSortOrder = null;

        List<String> names = userQueryRepository.findMenteesByMultipleConditionsOnPageable(pageable, location, category, menteeRatingSortOrder)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(names).containsExactly("user1", "user2");
    }

    @Test
    @DisplayName("멘티를 location + category 조건에 맞게 불러오는가?")
    void test_findMenteeDtosByMultipleConditionsOnPageable_eqLocationAndEqCategory() {
        Pageable pageable = PageRequest.of(0, 10);
        Location location = Location.GWANGJU_BUKGU;
        Category category = Category.PROGRAMMING_PYTHON;
        SortOrder menteeRatingSortOrder = null;

        List<String> names = userQueryRepository.findMenteesByMultipleConditionsOnPageable(pageable, location, category, menteeRatingSortOrder)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(names).containsExactly("user1", "user2");
    }

    @Test
    @DisplayName("멘티를 올바르게 페이징하여 불러오는가")
    void test_findMenteeDtosByMultipleConditionsOnPageable_OnPageable() {
        Pageable firstPageable = PageRequest.of(0, 1);
        Pageable secondPageable = PageRequest.of(1, 1);
        Location location = null;
        Category category = null;
        SortOrder menteeRatingSortOrder = null;

        List<String> firstNames = userQueryRepository.findMenteesByMultipleConditionsOnPageable(firstPageable, location, category, menteeRatingSortOrder)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(firstNames.size()).isEqualTo(1);

        List<String> secondNames = userQueryRepository.findMenteesByMultipleConditionsOnPageable(secondPageable, location, category, menteeRatingSortOrder)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(secondNames.size()).isEqualTo(1);

        Assertions.assertThat(firstNames).isNotEqualTo(secondNames);
    }
}