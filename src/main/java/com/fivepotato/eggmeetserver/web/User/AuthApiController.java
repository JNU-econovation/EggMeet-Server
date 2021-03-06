package com.fivepotato.eggmeetserver.web.user;

import com.fivepotato.eggmeetserver.domain.user.LoginType;
import com.fivepotato.eggmeetserver.dto.user.AppTokenDto;
import com.fivepotato.eggmeetserver.dto.user.AppTokenReissueDto;
import com.fivepotato.eggmeetserver.dto.user.UserSaveDto;
import com.fivepotato.eggmeetserver.dto.user.SocialTokenDto;
import com.fivepotato.eggmeetserver.exception.DuplicatedRequestException;
import com.fivepotato.eggmeetserver.exception.ErrorCode;
import com.fivepotato.eggmeetserver.service.user.AuthService;
import com.fivepotato.eggmeetserver.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AuthApiController {

    private final AuthService authService;
    private final UserService userSevice;

    @PostMapping("/auth/user")
    public ResponseEntity<Boolean> getIsExistUser(@RequestBody SocialTokenDto socialTokenDto) {
        String email = authService.getEmailBySocialTokenDto(socialTokenDto);
        boolean isExistUser = userSevice.getIsExistUserByEmail(socialTokenDto.getLoginType(), email);

        return new ResponseEntity<>(
                isExistUser,
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
    public ResponseEntity<Boolean> getIsBannedUserByEmail(@RequestParam(value = "loginType") LoginType loginType, @RequestParam(value = "email") String email) {
        return new ResponseEntity<>(
                userSevice.getIsBannedUser(loginType, email),
                HttpStatus.OK
        );
    }

    @PostMapping("/auth/register")
    public ResponseEntity<Void> register(@RequestBody UserSaveDto userSaveDto) {
        String email = authService.getEmailByUserSaveDto(userSaveDto);
        boolean isExistUser = userSevice.getIsExistUserByEmail(userSaveDto.getLoginType(), email);
        if (isExistUser) {
            throw new DuplicatedRequestException(ErrorCode.ALREADY_EXIST_USER + userSaveDto.getLoginType() + email);
        }

        boolean isBannedUser = userSevice.getIsBannedUser(userSaveDto.getLoginType(), email);
        if (isBannedUser) {
            throw new DuplicatedRequestException(ErrorCode.BAN_USER + userSaveDto.getLoginType() + email);
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
