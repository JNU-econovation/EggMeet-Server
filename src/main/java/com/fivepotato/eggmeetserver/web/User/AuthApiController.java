package com.fivepotato.eggmeetserver.web.User;

import com.fivepotato.eggmeetserver.dto.User.AppTokenDto;
import com.fivepotato.eggmeetserver.dto.User.AppTokenReissueDto;
import com.fivepotato.eggmeetserver.dto.User.UserSaveDto;
import com.fivepotato.eggmeetserver.dto.User.SocialTokenDto;
import com.fivepotato.eggmeetserver.service.User.AuthService;
import com.fivepotato.eggmeetserver.service.User.UserAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AuthApiController {

    private final AuthService authService;
    private final UserAdminService userAdminService;

    @PostMapping("/auth/user")
    public ResponseEntity<Boolean> getIsExistUser(@RequestBody SocialTokenDto socialTokenDto) {
        return new ResponseEntity<>(
                authService.getIsExistUser(socialTokenDto),
                HttpStatus.OK
        );
    }

    @GetMapping("/auth/user/name")
    public ResponseEntity<Boolean> getIsExistUserByName(@RequestParam(value = "name") String name) {
        return new ResponseEntity<>(
                authService.getIsExistUserByNickname(name),
                HttpStatus.OK
        );
    }

    @GetMapping("/auth/user/ban")
    public ResponseEntity<Boolean> getIsBannedUserByEmail(@RequestParam(value = "email") String email) {
        return new ResponseEntity<>(
                userAdminService.getIsBannedUserByEmail(email),
                HttpStatus.OK
        );
    }

    @PostMapping("/auth/register")
    public ResponseEntity<Void> register(@RequestBody UserSaveDto userSaveDto) {
        boolean isExistUser = authService.getIsExistUser(userSaveDto.toSocialTokenDto());
        if (isExistUser) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }

        authService.registerUser(userSaveDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AppTokenDto> login(@RequestBody SocialTokenDto socialTokenDto) {
        return new ResponseEntity<>(
                authService.getAppTokenDto(socialTokenDto),
                HttpStatus.OK
        );
    }

    @PostMapping("/auth/reissue")
    public ResponseEntity<AppTokenDto> reissue(@RequestBody AppTokenReissueDto appTokenReissueDto) {
        return new ResponseEntity<>(
                authService.reissueAppTokenDto(appTokenReissueDto),
                HttpStatus.OK
        );
    }
}
