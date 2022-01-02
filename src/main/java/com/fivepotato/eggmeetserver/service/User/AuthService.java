package com.fivepotato.eggmeetserver.service.User;

import com.fivepotato.eggmeetserver.domain.User.LoginType;
import com.fivepotato.eggmeetserver.domain.User.RefreshToken;
import com.fivepotato.eggmeetserver.domain.User.RefreshTokenRepository;
import com.fivepotato.eggmeetserver.domain.User.User;
import com.fivepotato.eggmeetserver.dto.Mentoring.MenteeAreaDto;
import com.fivepotato.eggmeetserver.dto.Mentoring.MentorAreaDto;
import com.fivepotato.eggmeetserver.dto.User.AppTokenDto;
import com.fivepotato.eggmeetserver.dto.User.UserSaveDto;
import com.fivepotato.eggmeetserver.dto.User.SocialTokenDto;
import com.fivepotato.eggmeetserver.exception.CustomAuthenticationException;
import com.fivepotato.eggmeetserver.exception.ErrorCode;
import com.fivepotato.eggmeetserver.exception.SystemIOException;
import com.fivepotato.eggmeetserver.provider.security.AppTokenProvider;
import com.google.gson.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${backdoor-token-secret}")
    private String BACKDOOR_TOKEN;
    private final String BACKDOOR_EMAIL = "test@test.com";
    private final String APPLE_DECODE_KEY_URL = "https://appleid.apple.com/auth/keys";

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final AppTokenProvider appTokenProvider;

    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MentoringService mentoringService;


    public boolean getIsExistUser(SocialTokenDto socialTokenDto) {
        String email = getEmailBySocialTokenDto(socialTokenDto);

        return userService.getIsExistUserByEmail(email);
    }

    public void registerUser(UserSaveDto userSaveDto) {
        if (userSaveDto.getSocialToken().equals(BACKDOOR_TOKEN)) {
            userSaveDto.setEmail(BACKDOOR_EMAIL);

        } else if (userSaveDto.getLoginType().equals(LoginType.APPLE)) {
            // TODO: 토큰 유효성 확인 함수 추가 및 추가된 함수에 맞게 getEmailBySocialTokenFromApple() 함수도 리펙토링
            // 시험용 토큰 때문에 윤성이와

            String email = getEmailBySocialTokenFromApple(userSaveDto.getSocialToken());
            userSaveDto.setEmail(email);

        } else if (userSaveDto.getLoginType().equals(LoginType.KAKAO)) {
            // TODO: 카카오 로그인 구현

        } else {
            throw new CustomAuthenticationException(ErrorCode.WRONG_LOGIN_TYPE);
        }

        User user = userSaveDto.toEntity(passwordEncoder);
        userService.createUser(user);

        if (userSaveDto.getMentorCategory() != null) {
            MentorAreaDto mentorAreaDto = MentorAreaDto.builder()
                    .mentor(user)
                    .category(userSaveDto.getMentorCategory())
                    .description(userSaveDto.getMentorDescription())
                    .career(userSaveDto.getMentorCareer())
                    .link(userSaveDto.getMentorLink())
                    .growthPoint(userSaveDto.getMentorGrowthPoint())
                    .build();

            mentoringService.setMentorArea(mentorAreaDto);
        }

        if (userSaveDto.getMenteeCategory() != null) {
            MenteeAreaDto menteeAreaDto = MenteeAreaDto.builder()
                    .mentee(user)
                    .category(userSaveDto.getMenteeCategory())
                    .description(userSaveDto.getMenteeDescription())
                    .build();

            mentoringService.setMenteeArea(menteeAreaDto);
        }
    }

    private String getEmailBySocialTokenFromApple(String socialToken) {
        PublicKey decodeKey = getAppleDecodeKey(socialToken);

        Claims userInfo = Jwts.parserBuilder()
                .setSigningKey(decodeKey)
                .build()
                .parseClaimsJws(socialToken)
                .getBody();
        JsonParser parser = new JsonParser();
        JsonObject userInfoObject = (JsonObject) parser.parse(new Gson().toJson(userInfo));

        return userInfoObject.get("email").getAsString();
    }

    private PublicKey getAppleDecodeKey(String socialToken) {
        StringBuffer response = new StringBuffer();
        try {
            URL url = new URL(APPLE_DECODE_KEY_URL);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
        } catch (IOException e) {
            throw new SystemIOException();
        }

        JsonParser parser = new JsonParser();
        JsonObject keys = (JsonObject) parser.parse(response.toString());
        JsonArray keyArray = (JsonArray) keys.get("keys");

        String[] decodeArray = socialToken.split("\\.");
        String header = new String(Base64.getDecoder().decode(decodeArray[0]));

        JsonElement kid = ((JsonObject) parser.parse(header)).get("kid");
        JsonElement alg = ((JsonObject) parser.parse(header)).get("alg");

        JsonObject decodeKeyObject = null;
        for (JsonElement key : keyArray) {
            JsonElement keyKid = ((JsonObject) key).get("kid");
            JsonElement keyAlg = ((JsonObject) key).get("alg");
            if (kid.equals(keyKid) && alg.equals(keyAlg)) {
                decodeKeyObject = (JsonObject) key;
            }
        }
        if (decodeKeyObject == null) {
            throw new CustomAuthenticationException(ErrorCode.WRONG_APPLE_SOCIAL_TOKEN);
        }

        return generateAppleDecodeKey(decodeKeyObject);
    }

    private PublicKey generateAppleDecodeKey(JsonObject decodeKeyObject) {
        String nStr = decodeKeyObject.get("n").toString();
        String eStr = decodeKeyObject.get("e").toString();

        byte[] nBytes = Base64.getUrlDecoder().decode(nStr.substring(1, nStr.length() - 1));
        byte[] eBytes = Base64.getUrlDecoder().decode(eStr.substring(1, eStr.length() - 1));

        BigInteger n = new BigInteger(1, nBytes);
        BigInteger e = new BigInteger(1, eBytes);

        try {
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            return publicKey;
        } catch (Exception exception) {
            throw new CustomAuthenticationException(ErrorCode.WRONG_APPLE_SOCIAL_TOKEN);
        }
    }

    public AppTokenDto getAppTokenDto(SocialTokenDto socialTokenDto) {
        String email = getEmailBySocialTokenDto(socialTokenDto);
        User user = userService.getUserByEmail(email);

        return issueAppTokenDto(user.getId());
    }

    private String getEmailBySocialTokenDto(SocialTokenDto socialTokenDto) throws CustomAuthenticationException {
        if (socialTokenDto.getSocialToken().equals(BACKDOOR_TOKEN)) {
            return BACKDOOR_EMAIL;

        } else if (socialTokenDto.getLoginType().equals(LoginType.APPLE)) {
            // TODO: 토큰 유효성 확인 함수 추가 및 추가된 함수에 맞게 getEmailBySocialTokenFromApple() 함수도 리펙토링
            // 시험용 토큰 때문에 윤성이와

            return getEmailBySocialTokenFromApple(socialTokenDto.getSocialToken());

        } else if (socialTokenDto.getLoginType().equals(LoginType.KAKAO)) {
            // TODO: 카카오 로그인 구현

        }

        throw new CustomAuthenticationException(ErrorCode.WRONG_LOGIN_TYPE);
    }

    private AppTokenDto issueAppTokenDto(Long id) {
        // String 형태의 user id를 기반으로 AuthenticationToken 생성
        String userId = id.toString();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, userId);

        // authenticate 메소드는 CustomUserDetailsService 의 loadUserByUsername 메서드 실행 (user id를 통해 유저를 감지)
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 인증 정보를 기반으로 JWT 토큰 생성
        AppTokenDto appTokenDto = appTokenProvider.generateAppTokenDto(authentication);

        // RefreshToken 저장
        // authentication.getName() => user id
        RefreshToken refreshToken = RefreshToken.builder()
                .userId(authentication.getName())
                .token(appTokenDto.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);

        // 토큰 발급
        return appTokenDto;
    }
}
