package com.fivepotato.eggmeetserver.service.User;

import com.fivepotato.eggmeetserver.domain.User.LoginType;
import com.fivepotato.eggmeetserver.domain.User.RefreshToken;
import com.fivepotato.eggmeetserver.domain.User.RefreshTokenRepository;
import com.fivepotato.eggmeetserver.domain.User.User;
import com.fivepotato.eggmeetserver.dto.Mentoring.MenteeAreaDto;
import com.fivepotato.eggmeetserver.dto.Mentoring.MentorAreaDto;
import com.fivepotato.eggmeetserver.dto.User.AppTokenDto;
import com.fivepotato.eggmeetserver.dto.User.AppTokenReissueDto;
import com.fivepotato.eggmeetserver.dto.User.UserSaveDto;
import com.fivepotato.eggmeetserver.dto.User.SocialTokenDto;
import com.fivepotato.eggmeetserver.exception.CustomAuthenticationException;
import com.fivepotato.eggmeetserver.exception.CustomTokenException;
import com.fivepotato.eggmeetserver.exception.ErrorCode;
import com.fivepotato.eggmeetserver.exception.SystemIOException;
import com.fivepotato.eggmeetserver.provider.security.AppTokenProvider;
import com.fivepotato.eggmeetserver.service.Mentoring.MentoringService;
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
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${backdoor-token-secret}")
    private String BACKDOOR_TOKEN;
    private final String BACKDOOR_EMAIL = "test@test.com";
    private final String APPLE_DECODE_KEY_URL = "https://appleid.apple.com/auth/keys";
    private final String APPLE_TOKEN_ISS = "https://appleid.apple.com";
    private final String APPLE_TOKEN_CLIENT_ID = "com.FivePotato.EggMeet";

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final AppTokenProvider appTokenProvider;

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MentoringService mentoringService;


    public boolean getIsExistUser(SocialTokenDto socialTokenDto) {
        String email = getEmailBySocialTokenDto(socialTokenDto);

        return userService.getIsExistUserByEmail(socialTokenDto.getLoginType(), email);
    }

    public boolean getIsExistName(String name) {
        return userService.getIsExistUserByName(name);
    }

    public void registerUser(UserSaveDto userSaveDto) {
        if (userSaveDto.getSocialToken().equals(BACKDOOR_TOKEN)) {
            userSaveDto.setEmail(BACKDOOR_EMAIL);

        } else if (userSaveDto.getLoginType().equals(LoginType.APPLE)) {
            validateAppleSocialToken(userSaveDto.getSocialToken());

            String email = getEmailByAppleSocialToken(userSaveDto.getSocialToken());
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

    public AppTokenDto getAppTokenDto(SocialTokenDto socialTokenDto) {
        String email = getEmailBySocialTokenDto(socialTokenDto);
        User user = userService.getUserByEmail(email);

        return issueAppTokenDto(user.getEmail());
    }

    private String getEmailBySocialTokenDto(SocialTokenDto socialTokenDto) {
        if (socialTokenDto.getSocialToken().equals(BACKDOOR_TOKEN)) {
            return BACKDOOR_EMAIL;

        } else if (socialTokenDto.getLoginType().equals(LoginType.APPLE)) {
            validateAppleSocialToken(socialTokenDto.getSocialToken());

            return getEmailByAppleSocialToken(socialTokenDto.getSocialToken());

        } else if (socialTokenDto.getLoginType().equals(LoginType.KAKAO)) {
            // TODO: 카카오 로그인 구현

        }

        throw new CustomAuthenticationException(ErrorCode.WRONG_LOGIN_TYPE);
    }

    private String getEmailByAppleSocialToken(String socialToken) {
        JsonObject tokenObject = getParsedObjectByAppleSocialToken(socialToken);

        return tokenObject.get("email").getAsString();
    }

    private void validateAppleSocialToken(String socialToken) {
        JsonObject tokenObject = getParsedObjectByAppleSocialToken(socialToken);

        String tokenIss = tokenObject.get("iss").toString().split("\"")[1];
        if (!tokenIss.equals(APPLE_TOKEN_ISS)) {
            throw new CustomAuthenticationException(ErrorCode.WRONG_APPLE_SOCIAL_TOKEN + " : wrong iss");
        }

        String tokenAud = tokenObject.get("aud").toString().split("\"")[1];
        if (!tokenAud.equals(APPLE_TOKEN_CLIENT_ID)) {
            throw new CustomAuthenticationException(ErrorCode.WRONG_APPLE_SOCIAL_TOKEN + " : wrong aud");
        }

        long expirationTime = Long.parseLong(tokenObject.get("exp").toString());
        long currentTime = (new Date()).getTime() / 1000; // 10자리 시간 형태인 apple token expiration time과 맞추기 위함
        if (expirationTime < currentTime) {
            throw new CustomAuthenticationException(ErrorCode.WRONG_APPLE_SOCIAL_TOKEN + " : expired token");
        }
    }

    private JsonObject getParsedObjectByAppleSocialToken(String socialToken) {
        PublicKey decodeKey = getAppleDecodeKey(socialToken);

        Claims userInfo = Jwts.parserBuilder()
                .setSigningKey(decodeKey)
                .build()
                .parseClaimsJws(socialToken)
                .getBody();
        JsonParser parser = new JsonParser();
        return (JsonObject) parser.parse(new Gson().toJson(userInfo));
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

    private AppTokenDto issueAppTokenDto(String email) {
        // 유저의 email을 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, email);

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

    @Transactional
    public AppTokenDto reissueAppTokenDto(AppTokenReissueDto appTokenReissueDto){
        appTokenProvider.validateToken(appTokenReissueDto.getRefreshToken());

        // issue 메소드의 UsernamePasswordAuthenticationToken와는 다르게 모든 정보가 들어 있음
        UsernamePasswordAuthenticationToken authentication = appTokenProvider.getAuthentication(appTokenReissueDto.getAccessToken());

        RefreshToken refreshToken = refreshTokenRepository.findByToken(appTokenReissueDto.getRefreshToken())
                .orElseThrow(() -> new CustomAuthenticationException(ErrorCode.NOT_EXIST_REFRESH_TOKEN));

        // authentication.getName() => user id
        if (!refreshToken.getUserId().equals(authentication.getName())) {
            throw new CustomTokenException(ErrorCode.WRONG_REFRESH_TOKEN);
        }

        AppTokenDto appTokenDto = appTokenProvider.generateAppTokenDto(authentication);
        refreshToken.setToken(appTokenDto.getRefreshToken());

        return appTokenDto;
    }
}
