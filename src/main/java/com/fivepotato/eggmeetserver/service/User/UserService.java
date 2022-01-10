package com.fivepotato.eggmeetserver.service.User;

import com.fivepotato.eggmeetserver.domain.Mentoring.Category;
import com.fivepotato.eggmeetserver.domain.User.*;
import com.fivepotato.eggmeetserver.dto.Mentoring.MentorDto;
import com.fivepotato.eggmeetserver.dto.Mentoring.SortOrder;
import com.fivepotato.eggmeetserver.dto.User.UserProfileDto;
import com.fivepotato.eggmeetserver.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserQueryRepository userQueryRepository;

    public void createUser(User user) {
        userRepository.save(user);
    }

    public Boolean getIsExistUserByEmail(LoginType loginType, String email) {
        return userRepository.existsByLoginTypeAndEmail(loginType, email);
    }

    public Boolean getIsExistUserByName(String name) {
        return userRepository.existsByName(name);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_MEMBER_BY_EMAIL + email));
    }

    public UserProfileDto getUserProfileDtoByEmail(String email) {
        User user = getUserByEmail(email);

        return new UserProfileDto(user);
    }

    public User getUserByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_MEMBER_BY_USERID + userId));
    }

    public List<MentorDto> getMentorDtosByMultipleConditionOnPageable(Pageable pageable,
                                                                      Location location,
                                                                      Category category,
                                                                      SortOrder mentorRatingSortOrder,
                                                                      SortOrder growthPointSortOrder) {
        Page<User> mentors = userQueryRepository.findMentorsByMultipleConditionsOnPageable(pageable, location, category, mentorRatingSortOrder, growthPointSortOrder);

        return mentors.get().map(MentorDto::new).collect(Collectors.toList());
    }
}
