package com.fivepotato.eggmeetserver.exception;

public enum ErrorCode {

    AUTHENTICATION_FAILED(401),
    SYSTEM_IO_ERROR(500);

    public static String WRONG_LOGIN_TYPE = "올바르지 않은 로그인 수단";
    public static String WRONG_APPLE_SOCIAL_TOKEN = AUTHENTICATION_FAILED.name() + " 올바르지 않은 Apple SocialToken";

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
