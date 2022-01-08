package com.fivepotato.eggmeetserver.web.User;

import com.fivepotato.eggmeetserver.dto.User.UserProfileDto;
import com.fivepotato.eggmeetserver.service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
