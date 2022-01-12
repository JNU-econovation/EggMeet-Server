package com.fivepotato.eggmeetserver.web.User;

import com.fivepotato.eggmeetserver.domain.Mentoring.Category;
import com.fivepotato.eggmeetserver.domain.User.Location;
import com.fivepotato.eggmeetserver.dto.Mentoring.MenteeDto;
import com.fivepotato.eggmeetserver.dto.Mentoring.MentorDto;
import com.fivepotato.eggmeetserver.dto.Mentoring.SortOrder;
import com.fivepotato.eggmeetserver.dto.User.UserProfileDto;
import com.fivepotato.eggmeetserver.service.User.UserService;
import com.fivepotato.eggmeetserver.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @GetMapping("/user/profile")
    public ResponseEntity<UserProfileDto> getUserProfileDtoById(@RequestParam(value = "id") Long userId) {
        return new ResponseEntity<>(
                userService.getUserProfileDtoByUserId(userId),
                HttpStatus.OK
        );
    }

    @GetMapping("/user/profile/me")
    public ResponseEntity<UserProfileDto> getMyUserProfileDto() {
        Long myId = SecurityUtils.getCurrentUserId();

        return new ResponseEntity<>(
                userService.getUserProfileDtoByUserId(myId),
                HttpStatus.OK
        );
    }

    @GetMapping("/user/mentor")
    public ResponseEntity<List<MentorDto>> getMentorDtosByMultipleConditionsOnPageable(Pageable pageable,
                                                                                      @RequestParam(value = "location", required = false) Location location,
                                                                                      @RequestParam(value = "category", required = false) Category category,
                                                                                      @RequestParam(value = "ratingSort", required = false) SortOrder mentorRatingSortOrder,
                                                                                      @RequestParam(value = "growthPointSort", required = false) SortOrder growthPointSortOrder) {
        return new ResponseEntity<>(
                userService.getMentorDtosByMultipleConditionsOnPageable(
                        pageable,
                        location,
                        category,
                        mentorRatingSortOrder,
                        growthPointSortOrder
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/user/mentee")
    public ResponseEntity<List<MenteeDto>> getMenteeDtosByMultipleConditionsOnPageable(Pageable pageable,
                                                                                      @RequestParam(value = "location", required = false) Location location,
                                                                                      @RequestParam(value = "category", required = false) Category category,
                                                                                      @RequestParam(value = "ratingSort", required = false) SortOrder menteeRatingSortOrder) {
        return new ResponseEntity<>(
                userService.getMenteeDtosByMultipleConditionsOnPageable(
                        pageable,
                        location,
                        category,
                        menteeRatingSortOrder
                ),
                HttpStatus.OK
        );
    }
}
