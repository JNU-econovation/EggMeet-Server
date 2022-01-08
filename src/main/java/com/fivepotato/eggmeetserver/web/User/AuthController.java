package com.fivepotato.eggmeetserver.web.User;

import com.fivepotato.eggmeetserver.dto.User.AppTokenDto;
import com.fivepotato.eggmeetserver.dto.User.AppTokenReissueDto;
import com.fivepotato.eggmeetserver.dto.User.UserSaveDto;
import com.fivepotato.eggmeetserver.dto.User.SocialTokenDto;
import com.fivepotato.eggmeetserver.service.User.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @GetMapping("/auth/user")
    public ResponseEntity<Boolean> getIsExistUser(@RequestBody SocialTokenDto socialTokenDto) {
        return new ResponseEntity<>(
                authService.getIsExistUser(socialTokenDto),
                HttpStatus.OK
        );
    }

    @PostMapping("/auth/register")
    public ResponseEntity<Void> register(@RequestBody UserSaveDto userSaveDto) {
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

    @GetMapping("/auth/reissue")
    public ResponseEntity<AppTokenDto> reissue(@RequestBody AppTokenReissueDto appTokenReissueDto) {
        return new ResponseEntity<>(
                authService.reissueAppTokenDto(appTokenReissueDto),
                HttpStatus.OK
        );
    }
}
