package com.fivepotato.eggmeetserver.web.User;

import com.fivepotato.eggmeetserver.domain.Mentoring.Category;
import com.fivepotato.eggmeetserver.domain.User.Location;
import com.fivepotato.eggmeetserver.dto.Mentoring.MenteeDto;
import com.fivepotato.eggmeetserver.dto.Mentoring.MentorDto;
import com.fivepotato.eggmeetserver.dto.Mentoring.SortOrder;
import com.fivepotato.eggmeetserver.dto.User.UserProfileDto;
import com.fivepotato.eggmeetserver.service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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

    @GetMapping("/user")
    public ResponseEntity<UserProfileDto> getUserProfileDtoByEmail(@RequestParam(value = "email") String email) {
        return new ResponseEntity<>(
                userService.getUserProfileDtoByEmail(email),
                HttpStatus.OK
        );
    }

    @GetMapping("/user/mentor")
    public ResponseEntity<List<MentorDto>> getMentorDtosByMultipleConditionOnPageable(Pageable pageable,
                                                                                      @RequestParam(value = "location", required = false) Location location,
                                                                                      @RequestParam(value = "category", required = false) Category category,
                                                                                      @RequestParam(value = "ratingSort", required = false) SortOrder mentorRatingSortOrder,
                                                                                      @RequestParam(value = "growthPointSort", required = false) SortOrder growthPointSortOrder) {
        return new ResponseEntity<>(
                userService.getMentorDtosByMultipleConditionOnPageable(
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
    public ResponseEntity<List<MenteeDto>> getMenteeDtosByMultipleConditionOnPageable(Pageable pageable,
                                                                                      @RequestParam(value = "location", required = false) Location location,
                                                                                      @RequestParam(value = "category", required = false) Category category,
                                                                                      @RequestParam(value = "ratingSort", required = false) SortOrder menteeRatingSortOrder) {
        return new ResponseEntity<>(
                null,
                HttpStatus.OK
        );
    }
}
