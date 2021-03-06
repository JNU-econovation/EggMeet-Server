package com.fivepotato.eggmeetserver;

import com.fivepotato.eggmeetserver.domain.mentoring.*;
import com.fivepotato.eggmeetserver.domain.user.*;
import com.fivepotato.eggmeetserver.dto.mentoring.SortOrder;
import com.fivepotato.eggmeetserver.service.user.UserAdminService;
import org.junit.jupiter.api.*;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserQueryRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private MentorAreaRepository mentorAreaRepository;

    @Autowired
    private MenteeAreaRepository menteeAreaRepository;

    @Autowired
    private UserQueryRepository userQueryRepository;

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
        MenteeArea user1MenteeArea = MenteeArea.builder()
                .mentee(user1)
                .category(Category.IT_WEB)
                .description("user1Description")
                .build();

        userRepository.save(user1);
        mentorAreaRepository.save(user1MentorArea);
        menteeAreaRepository.save(user1MenteeArea);

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
        MenteeArea user2MenteeArea = MenteeArea.builder()
                .mentee(user2)
                .category(Category.IT_WEB)
                .description("user2Description")
                .build();


        userRepository.save(user2);
        mentorAreaRepository.save(user2MentorArea);
        menteeAreaRepository.save(user2MenteeArea);

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
        MentorArea user3MentorArea = MentorArea.builder()
                .mentor(user3)
                .category(Category.IT_WEB)
                .description("user3Description")
                .career("user3Career")
                .link("user3Link")
                .growthCost(3)
                .build();
        MenteeArea user3MenteeArea = MenteeArea.builder()
                .mentee(user3)
                .category(Category.IT_APP)
                .description("user3Description")
                .build();


        userRepository.save(user3);
        mentorAreaRepository.save(user3MentorArea);
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
        MentorArea user4MentorArea = MentorArea.builder()
                .mentor(user4)
                .category(Category.IT_WEB)
                .description("user4Description")
                .career("user4Career")
                .link("user4Link")
                .growthCost(4)
                .build();
        MenteeArea user4MenteeArea = MenteeArea.builder()
                .mentee(user4)
                .category(Category.IT_APP)
                .description("user4Description")
                .build();


        userRepository.save(user4);
        mentorAreaRepository.save(user4MentorArea);
        menteeAreaRepository.save(user4MenteeArea);

        User user5 = User.builder()
                .nickname("user5")
                .age(30)
                .sex(Sex.FEMALE)
                .location(Location.GWANGJU_BUKGU)
                .description("user5Description")
                .pictureIndex(0)
                .offlineAvailable(false)
                .onlineAvailable(false)
                .loginType(LoginType.APPLE)
                .email("user5@user5.com")
                .encodedEmail("user5@user5.com")
                .role(Role.ROLE_USER)
                .build();
        MentorArea user5MentorArea = MentorArea.builder()
                .mentor(user5)
                .category(Category.IT_APP)
                .description("user5Description")
                .career("user5Career")
                .link("user5Link")
                .growthCost(0)
                .build();
        MenteeArea user5MenteeArea = MenteeArea.builder()
                .mentee(user5)
                .category(Category.IT_BACKEND)
                .description("user5Description")
                .build();


        userRepository.save(user5);
        mentorAreaRepository.save(user5MentorArea);
        menteeAreaRepository.save(user5MenteeArea);
    }


    @Test
    @DisplayName("????????? location ????????? ?????? ????????????????")
    void test_findMentorDtosByMultipleConditionsOnPageable_eqLocation() {
        Pageable pageable = PageRequest.of(0, 10);
        Location location = Location.GWANGJU_BUKGU;
        Sex sex = null;
        List<Integer> ages = null;
        Boolean onlineAvailable = null;
        Boolean offlineAvailable = null;
        Category category = null;
        SortOrder mentorRatingSortOrder = null;
        SortOrder growthPointSortOrder = null;

        List<String> names = userQueryRepository.findMentorsByMultipleConditionsOnPageable(pageable, location, sex, ages, onlineAvailable, offlineAvailable, category, mentorRatingSortOrder, growthPointSortOrder)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(names).containsExactly("user1", "user2", "user5");
    }

    @Test
    @DisplayName("????????? sex ????????? ?????? ????????????????")
    void test_findMentorDtosByMultipleConditionsOnPageable_eqSex() {
        Pageable pageable = PageRequest.of(0, 10);
        Location location = null;
        Sex sex = Sex.MALE;
        List<Integer> ages = null;
        Boolean onlineAvailable = null;
        Boolean offlineAvailable = null;
        Category category = null;
        SortOrder mentorRatingSortOrder = null;
        SortOrder growthPointSortOrder = null;

        List<String> names = userQueryRepository.findMentorsByMultipleConditionsOnPageable(pageable, location, sex, ages, onlineAvailable, offlineAvailable, category, mentorRatingSortOrder, growthPointSortOrder)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(names).containsExactly("user1", "user2");
    }

    @Test
    @DisplayName("????????? age ????????? ?????? ????????????????")
    void test_findMentorDtosByMultipleConditionsOnPageable_eqAge() {
        Pageable pageable = PageRequest.of(0, 10);
        Location location = null;
        Sex sex = null;
        List<Integer> ages = List.of(20);
        Boolean onlineAvailable = null;
        Boolean offlineAvailable = null;
        Category category = null;
        SortOrder mentorRatingSortOrder = null;
        SortOrder growthPointSortOrder = null;

        List<String> names = userQueryRepository.findMentorsByMultipleConditionsOnPageable(pageable, location, sex, ages, onlineAvailable, offlineAvailable, category, mentorRatingSortOrder, growthPointSortOrder)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(names).containsExactly("user3", "user4");
    }

    @Test
    @DisplayName("????????? ?????? ?????? age ????????? ?????? ????????????????")
    void test_findMentorDtosByMultipleConditionsOnPageable_eqAges() {
        Pageable pageable = PageRequest.of(0, 10);
        Location location = null;
        Sex sex = null;
        List<Integer> ages = List.of(10, 20);
        Boolean onlineAvailable = null;
        Boolean offlineAvailable = null;
        Category category = null;
        SortOrder mentorRatingSortOrder = null;
        SortOrder growthPointSortOrder = null;

        List<String> names = userQueryRepository.findMentorsByMultipleConditionsOnPageable(pageable, location, sex, ages, onlineAvailable, offlineAvailable, category, mentorRatingSortOrder, growthPointSortOrder)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(names).containsExactly("user1", "user2", "user3", "user4");
    }

    @Test
    @DisplayName("????????? onlineAvailable/offlineAvailable ????????? ?????? ????????????????")
    void test_findMentorDtosByMultipleConditionsOnPageable_eqIsOnlineAvailableAndEqIsOfflineAvailable() {
        Pageable pageable = PageRequest.of(0, 10);
        Location location = null;
        Sex sex = null;
        List<Integer> ages = null;
        Boolean onlineAvailable = false;
        Boolean offlineAvailable = false;
        Category category = null;
        SortOrder mentorRatingSortOrder = null;
        SortOrder growthPointSortOrder = null;

        List<String> names = userQueryRepository.findMentorsByMultipleConditionsOnPageable(pageable, location, sex, ages, onlineAvailable, offlineAvailable, category, mentorRatingSortOrder, growthPointSortOrder)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(names).containsExactly("user4", "user5");
    }

    @Test
    @DisplayName("????????? category ????????? ?????? ????????????????")
    void test_findMentorDtosByMultipleConditionsOnPageable_eqCategory() {
        Pageable pageable = PageRequest.of(0, 10);
        Location location = null;
        Sex sex = null;
        List<Integer> ages = null;
        Boolean onlineAvailable = null;
        Boolean offlineAvailable = null;
        Category category = Category.IT_WEB;
        SortOrder mentorRatingSortOrder = null;
        SortOrder growthPointSortOrder = null;

        List<String> names = userQueryRepository.findMentorsByMultipleConditionsOnPageable(pageable, location, sex, ages, onlineAvailable, offlineAvailable, category, mentorRatingSortOrder, growthPointSortOrder)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(names).containsExactly("user3", "user4");
    }

    @Test
    @DisplayName("????????? location + category ????????? ?????? ????????????????")
    void test_findMentorDtosByMultipleConditionsOnPageable_eqLocationAndEqCategory() {
        Pageable pageable = PageRequest.of(0, 10);
        Location location = Location.GWANGJU_BUKGU;
        Sex sex = null;
        List<Integer> ages = null;
        Boolean onlineAvailable = null;
        Boolean offlineAvailable = null;
        Category category = Category.IT_APP;
        SortOrder mentorRatingSortOrder = null;
        SortOrder growthPointSortOrder = null;

        List<String> names = userQueryRepository.findMentorsByMultipleConditionsOnPageable(pageable, location, sex, ages, onlineAvailable, offlineAvailable, category, mentorRatingSortOrder, growthPointSortOrder)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(names).containsExactly("user2", "user5");
    }

    @Test
    @DisplayName("????????? location + category ????????? ?????? growthPoint ??????????????? ???????????? ????????????????")
    void test_findMentorDtosByMultipleConditionsOnPageable_eqLocationAndEqCategory_orderByGrowthPointAsc() {
        Pageable pageable = PageRequest.of(0, 10);
        Location location = Location.GWANGJU_BUKGU;
        Sex sex = null;
        List<Integer> ages = null;
        Boolean onlineAvailable = null;
        Boolean offlineAvailable = null;
        Category category = Category.IT_APP;
        SortOrder mentorRatingSortOrder = null;
        SortOrder growthPointSortOrder = SortOrder.ASCENDING;

        List<String> names = userQueryRepository.findMentorsByMultipleConditionsOnPageable(pageable, location, sex, ages, onlineAvailable, offlineAvailable, category, mentorRatingSortOrder, growthPointSortOrder)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(names).containsExactly("user5", "user2");
    }

    @Test
    @DisplayName("????????? growthPoint ???????????? ???????????? ????????????????")
    void test_findMentorDtosByMultipleConditionsOnPageable_orderByGrowthPointAsc() {
        Pageable pageable = PageRequest.of(0, 10);
        Location location = null;
        Sex sex = null;
        List<Integer> ages = null;
        Boolean onlineAvailable = null;
        Boolean offlineAvailable = null;
        Category category = null;
        SortOrder mentorRatingSortOrder = null;
        SortOrder growthPointSortOrder = SortOrder.ASCENDING;

        List<String> names = userQueryRepository.findMentorsByMultipleConditionsOnPageable(pageable, location, sex, ages, onlineAvailable, offlineAvailable, category, mentorRatingSortOrder, growthPointSortOrder)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(names).containsExactly("user5", "user1", "user2", "user3", "user4");
    }

    @Test
    @DisplayName("????????? growthPoint ???????????? ???????????? ????????????????")
    void test_findMentorDtosByMultipleConditionsOnPageable_orderByGrowthPointDesc() {
        Pageable pageable = PageRequest.of(0, 10);
        Location location = null;
        Sex sex = null;
        List<Integer> ages = null;
        Boolean onlineAvailable = null;
        Boolean offlineAvailable = null;
        Category category = null;
        SortOrder mentorRatingSortOrder = null;
        SortOrder growthPointSortOrder = SortOrder.DESCENDING;

        List<String> names = userQueryRepository.findMentorsByMultipleConditionsOnPageable(pageable, location, sex, ages, onlineAvailable, offlineAvailable, category, mentorRatingSortOrder, growthPointSortOrder)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(names).containsExactly("user4", "user3", "user2", "user1", "user5");
    }

    @Test
    @DisplayName("????????? ???????????? ??????????????? ???????????????")
    void test_findMentorDtosByMultipleConditionsOnPageable_OnPageable() {
        Pageable firstPageable = PageRequest.of(0, 1);
        Pageable secondPageable = PageRequest.of(1, 1);
        Location location = null;
        Sex sex = null;
        List<Integer> ages = null;
        Boolean onlineAvailable = null;
        Boolean offlineAvailable = null;
        Category category = null;
        SortOrder mentorRatingSortOrder = null;
        SortOrder growthPointSortOrder = null;

        List<String> firstNames = userQueryRepository.findMentorsByMultipleConditionsOnPageable(firstPageable, location, sex, ages, onlineAvailable, offlineAvailable, category, mentorRatingSortOrder, growthPointSortOrder)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(firstNames.size()).isEqualTo(1);

        List<String> secondNames = userQueryRepository.findMentorsByMultipleConditionsOnPageable(secondPageable, location, sex, ages, onlineAvailable, offlineAvailable, category, mentorRatingSortOrder, growthPointSortOrder)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(secondNames.size()).isEqualTo(1);

        Assertions.assertThat(firstNames).isNotEqualTo(secondNames);
    }

    @Test
    @DisplayName("????????? location ????????? ?????? ???????????????")
    void test_findMenteeDtosByMultipleConditionsOnPageable_eqLocation() {
        Pageable pageable = PageRequest.of(0, 10);
        Location location = Location.GWANGJU_BUKGU;
        Sex sex = null;
        List<Integer> ages = null;
        Boolean onlineAvailable = null;
        Boolean offlineAvailable = null;
        Category category = null;
        SortOrder menteeRatingSortOrder = null;

        List<String> names = userQueryRepository.findMenteesByMultipleConditionsOnPageable(pageable, location, sex, ages, onlineAvailable, offlineAvailable, category, menteeRatingSortOrder)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(names).containsExactly("user1", "user2", "user5");
    }

    @Test
    @DisplayName("????????? category ????????? ?????? ????????????????")
    void test_findMenteeDtosByMultipleConditionsOnPageable_eqMenteeCategory() {
        Pageable pageable = PageRequest.of(0, 10);
        Location location = null;
        Sex sex = null;
        List<Integer> ages = null;
        Boolean onlineAvailable = null;
        Boolean offlineAvailable = null;
        Category category = Category.IT_WEB;
        SortOrder menteeRatingSortOrder = null;


        List<String> names = userQueryRepository.findMenteesByMultipleConditionsOnPageable(pageable, location, sex, ages, onlineAvailable, offlineAvailable, category, menteeRatingSortOrder)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(names).containsExactly("user1", "user2");
    }

    @Test
    @DisplayName("????????? location + category ????????? ?????? ????????????????")
    void test_findMenteeDtosByMultipleConditionsOnPageable_eqLocationAndEqCategory() {
        Pageable pageable = PageRequest.of(0, 10);
        Location location = Location.GWANGJU_BUKGU;
        Sex sex = null;
        List<Integer> ages = null;
        Boolean onlineAvailable = null;
        Boolean offlineAvailable = null;
        Category category = Category.IT_WEB;
        SortOrder menteeRatingSortOrder = null;

        List<String> names = userQueryRepository.findMenteesByMultipleConditionsOnPageable(pageable, location, sex, ages, onlineAvailable, offlineAvailable, category, menteeRatingSortOrder)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(names).containsExactly("user1", "user2");
    }

    @Test
    @DisplayName("????????? ???????????? ??????????????? ???????????????")
    void test_findMenteeDtosByMultipleConditionsOnPageable_OnPageable() {
        Pageable firstPageable = PageRequest.of(0, 1);
        Pageable secondPageable = PageRequest.of(1, 1);
        Location location = null;
        Sex sex = null;
        List<Integer> ages = null;
        Boolean onlineAvailable = null;
        Boolean offlineAvailable = null;
        Category category = null;
        SortOrder menteeRatingSortOrder = null;

        List<String> firstNames = userQueryRepository.findMenteesByMultipleConditionsOnPageable(firstPageable, location, sex, ages, onlineAvailable, offlineAvailable, category, menteeRatingSortOrder)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(firstNames.size()).isEqualTo(1);

        List<String> secondNames = userQueryRepository.findMenteesByMultipleConditionsOnPageable(secondPageable, location, sex, ages, onlineAvailable, offlineAvailable, category, menteeRatingSortOrder)
                .stream().map(User::getNickname).collect(Collectors.toList());
        Assertions.assertThat(secondNames.size()).isEqualTo(1);

        Assertions.assertThat(firstNames).isNotEqualTo(secondNames);
    }
}