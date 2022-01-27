package com.fivepotato.eggmeetserver.service.user;

import com.fivepotato.eggmeetserver.domain.ban.BanRepository;
import com.fivepotato.eggmeetserver.domain.mentoring.*;
import com.fivepotato.eggmeetserver.domain.user.*;
import com.fivepotato.eggmeetserver.dto.mentoring.*;
import com.fivepotato.eggmeetserver.dto.user.UserProfileDto;
import com.fivepotato.eggmeetserver.dto.user.UserProfileUpdateDto;
import com.fivepotato.eggmeetserver.exception.ErrorCode;
import com.fivepotato.eggmeetserver.exception.NoContentException;
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
    private final MentorAreaRepository mentorAreaRepository;
    private final MenteeAreaRepository menteeAreaRepository;
    private final BanRepository banRepository;


    public void createUser(User user) {
        userRepository.save(user);
    }

    public void setMentorArea(MentorAreaDto mentorAreaDto) {
        mentorAreaRepository.save(mentorAreaDto.toEntity());
    }

    public void setMenteeArea(MenteeAreaDto menteeAreaDto) {
        menteeAreaRepository.save(menteeAreaDto.toEntity());
    }

    public MentorArea getMentorAreaByMentorId(Long mentorId) {
        return mentorAreaRepository.findByMentor_Id(mentorId)
                .orElseThrow(() -> new NoContentException(ErrorCode.NO_MENTOR_AREA_BY_USER + mentorId));
    }

    public MenteeArea getMenteeAreaByMentorId(Long menteeId) {
        return menteeAreaRepository.findByMentee_Id(menteeId)
                .orElseThrow(() -> new NoContentException(ErrorCode.NO_MENTEE_AREA_BY_USER + menteeId));
    }

    @Transactional
    public void updateMentorAreaByMentorId(Long mentorId, UserProfileUpdateDto userProfileUpdateDto) {
        try {
            MentorArea mentorArea = getMentorAreaByMentorId(mentorId);
            mentorArea.updateMentorAreaByUserProfileUpdateDto(userProfileUpdateDto);
        } catch (NoContentException ignored) {

        }
    }

    @Transactional
    public void updateMenteeAreaByMenteeId(Long menteeId, UserProfileUpdateDto userProfileUpdateDto) {
        try {
            MenteeArea menteeArea = getMenteeAreaByMentorId(menteeId);
            menteeArea.updateMenteeAreaByUserProfileUpdateDto(userProfileUpdateDto);
        } catch (NoContentException ignored) {

        }
    }

    public Boolean getIsExistUserByEmail(LoginType loginType, String email) {
        if (AuthService.BACKDOOR_EMAIL.equals(email)) {
            return false;
        }

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
                                                                       List<Integer> ages,
                                                                       Boolean onlineAvailable,
                                                                       Boolean offlineAvailable,
                                                                       Category category,
                                                                       SortOrder mentorRatingSortOrder,
                                                                       SortOrder growthPointSortOrder) {
        List<User> users = userQueryRepository.findMentorsByMultipleConditionsOnPageable(pageable, location, sex, ages, onlineAvailable, offlineAvailable, category, mentorRatingSortOrder, growthPointSortOrder);

        return users.stream().map(MentorDto::new).collect(Collectors.toList());
    }

    public List<MenteeDto> getMenteeDtosByMultipleConditionsOnPageable(Pageable pageable,
                                                                       Location location,
                                                                       Sex sex,
                                                                       List<Integer> ages,
                                                                       Boolean onlineAvailable,
                                                                       Boolean offlineAvailable,
                                                                       Category category,
                                                                       SortOrder menteeRatingSortOrder) {
        List<User> users = userQueryRepository.findMenteesByMultipleConditionsOnPageable(pageable, location, sex, ages, onlineAvailable, offlineAvailable, category, menteeRatingSortOrder);

        return users.stream().map(MenteeDto::new).collect(Collectors.toList());
    }

    public boolean getIsBannedUser(LoginType loginType, String email) {
        return banRepository.existsByBannedLoginTypeAndBannedEmail(loginType, email);
    }

    public List<MentorDto> getMentorDtosByNicknameSearching(Pageable pageable, String nickname) {
        return userRepository.findUsersByMentorAreaIsNotNullAndNicknameContaining(pageable, nickname)
                .stream().map(MentorDto::new).collect(Collectors.toList());
    }

    public List<MenteeDto> getMenteeDtosByNicknameSearching(Pageable pageable, String nickname) {
        return userRepository.findUsersByMenteeAreaIsNotNullAndNicknameContaining(pageable, nickname)
                .stream().map(MenteeDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void updateUserProfile(Long userId, UserProfileUpdateDto userProfileUpdateDto) {
        User user = getUserByUserId(userId);
        user.updateUserProfile(userProfileUpdateDto);
        updateMentorAreaByMentorId(userId, userProfileUpdateDto);
        updateMenteeAreaByMenteeId(userId, userProfileUpdateDto);
    }
}
