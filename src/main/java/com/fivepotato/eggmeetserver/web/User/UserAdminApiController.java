package com.fivepotato.eggmeetserver.web.User;

import com.fivepotato.eggmeetserver.dto.User.UserProfileDto;
import com.fivepotato.eggmeetserver.dto.ban.BanDto;
import com.fivepotato.eggmeetserver.service.User.UserAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserAdminApiController {

    private final UserAdminService userAdminService;

    @PostMapping("/user/ban")
    public ResponseEntity<Void> banUser(@RequestBody BanDto banDto) {
        userAdminService.banUser(banDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user/list")
    public ResponseEntity<List<UserProfileDto>> getAllUserProfileDtos() {
        return new ResponseEntity<>(
                userAdminService.getAllUserProfileDto(),
                HttpStatus.OK
        );
    }
}
