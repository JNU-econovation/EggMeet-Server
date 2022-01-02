package com.fivepotato.eggmeetserver.exception;

public enum ErrorCode {

    AUTHENTICATION_FAILED(401),
    TOKEN_ERROR(401),
    SYSTEM_IO_ERROR(500);

    public static String WRONG_LOGIN_TYPE = "올바르지 않은 로그인 수단";
    public static String WRONG_APPLE_SOCIAL_TOKEN = " 올바르지 않은 Apple SocialToken";
    public static String WRONG_REFRESH_TOKEN = "잘못된 RefreshToken";
    public static String NOT_EXIST_REFRESH_TOKEN = "해당하는 RefreshToken이 존재하지 않음";

    public static String WRONG_FORMED_TOKEN = "잘못된 구성된 JWT 토큰 값";
    public static String EXPIRED_TOKEN = "만료된 JWT 토큰";
    public static String UNSUPPORTED_TOKEN = "지원되지 않는 JWT 토큰";
    public static String WRONG_TOKEN = "잘못된 JWT 토큰";


    public static String NO_MEMBER_BY_EMAIL = "해당 이메일과 일치하는 유저가 존재하지 않음 : email = ";
    public static String NO_MEMBER_BY_USERID = "해당 ID와 일치하는 유저가 존재하지 않음 : id = ";

    public static String SYSTEM_IO_ERROR_MESSAGE = SYSTEM_IO_ERROR.name() + " 서버의 IO 과정에서 문제 발생";


    private final int status;

    ErrorCode(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
