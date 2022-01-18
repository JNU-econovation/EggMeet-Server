package com.fivepotato.eggmeetserver.service.user;

import com.fivepotato.eggmeetserver.domain.ban.BanRepository;
import com.fivepotato.eggmeetserver.domain.mentoring.Category;
import com.fivepotato.eggmeetserver.domain.user.*;
import com.fivepotato.eggmeetserver.dto.mentoring.MenteeDto;
import com.fivepotato.eggmeetserver.dto.mentoring.MentorDto;
import com.fivepotato.eggmeetserver.dto.mentoring.SortOrder;
import com.fivepotato.eggmeetserver.dto.user.UserProfileDto;
import com.fivepotato.eggmeetserver.dto.user.UserProfileUpdateDto;
import com.fivepotato.eggmeetserver.exception.ErrorCode;
import com.fivepotato.eggmeetserver.exception.NoContentException;
import com.fivepotato.eggmeetserver.service.mentoring.MentoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserQueryRepository userQueryRepository;
    private final MentoringService mentoringService;
    private final BanRepository banRepository;


    public void createUser(User user) {
        userRepository.save(user);
    }

    public Boolean getIsExistUserByEmail(LoginType loginType, String email) {
        return userRepository.existsByLoginTypeAndEmail(loginType, email);
    }

    public Boolean getIsExistUserByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NoContentException(ErrorCode.NO_MEMBER_BY_EMAIL + email));
    }

    public UserProfileDto getUserProfileDtoByEmail(String email) {
        User user = getUserByEmail(email);

        return new UserProfileDto(user);
    }

    public User getUserByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoContentException(ErrorCode.NO_MEMBER_BY_USERID + userId));
    }

    public UserProfileDto getUserProfileDtoByUserId(Long userId) {
        User user = getUserByUserId(userId);

        return new UserProfileDto(user);
    }

    public List<MentorDto> getMentorDtosByMultipleConditionsOnPageable(Pageable pageable,
                                                                      Location location,
                                                                      Sex sex,
                                                                      Integer age,
                                                                      Boolean isOnlineAvailable,
                                                                      Boolean isOfflineAvailable,
                                                                      Category category,
                                                                      SortOrder mentorRatingSortOrder,
                                                                      SortOrder growthPointSortOrder) {
        List<User> users = userQueryRepository.findMentorsByMultipleConditionsOnPageable(pageable, location, sex, age, isOnlineAvailable, isOfflineAvailable, category, mentorRatingSortOrder, growthPointSortOrder);

        return users.stream().map(MentorDto::new).collect(Collectors.toList());
    }

    public List<MenteeDto> getMenteeDtosByMultipleConditionsOnPageable(Pageable pageable,
                                                                      Location location,
                                                                       Sex sex,
                                                                       Integer age,
                                                                       Boolean isOnlineAvailable,
                                                                       Boolean isOfflineAvailable,
                                                                      Category category,
                                                                      SortOrder menteeRatingSortOrder) {
        List<User> users = userQueryRepository.findMenteesByMultipleConditionsOnPageable(pageable, location, sex, age, isOnlineAvailable, isOfflineAvailable, category, menteeRatingSortOrder);

        return users.stream().map(MenteeDto::new).collect(Collectors.toList());
    }

    public boolean getIsBannedUser(LoginType loginType, String email) {
        return banRepository.existsByBannedLoginTypeAndBannedEmail(loginType, email);
    }

    @Transactional
    public void updateUserProfile(Long userId, UserProfileUpdateDto userProfileUpdateDto) {
        User user = getUserByUserId(userId);
        user.updateUserProfile(userProfileUpdateDto);
        mentoringService.updateMentorAreaByMentorId(userId, userProfileUpdateDto);
        mentoringService.updateMenteeAreaByMenteeId(userId, userProfileUpdateDto);
    }
}
