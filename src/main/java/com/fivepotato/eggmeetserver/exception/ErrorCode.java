package com.fivepotato.eggmeetserver.exception;

public enum ErrorCode {

    AUTHENTICATION_FAILED(401),
    TOKEN_ERROR(401),
    SYSTEM_IO_ERROR(500);

    public static String WRONG_LOGIN_TYPE = "올바르지 않은 로그인 수단";
    public static String WRONG_APPLE_SOCIAL_TOKEN = "올바르지 않은 Apple Social Token";
    public static String WRONG_REFRESH_TOKEN = "잘못된 Refresh Token";
    public static String NOT_EXIST_REFRESH_TOKEN = "해당하는 Refresh Token이 존재하지 않음";
    public static String ERROR_SECURITY_CONTEXT = "Security Context에 인증 정보가 올바르게 저장되지 않음";

    public static String WRONG_FORMED_TOKEN = "잘못된 구성된 JWT 토큰 값";
    public static String EXPIRED_TOKEN = "만료된 JWT 토큰";
    public static String UNSUPPORTED_TOKEN = "지원되지 않는 JWT 토큰";
    public static String WRONG_TOKEN = "잘못된 JWT 토큰";


    public static String NO_MEMBER_BY_EMAIL = "해당 이메일과 일치하는 유저가 존재하지 않음 : email = ";
    public static String NO_MEMBER_BY_USERID = "해당 ID와 일치하는 유저가 존재하지 않음 : id = ";
    public static String NO_MENTOR_AREA_BY_USER = "해당 유저는 멘토 설정이 되어있지 않음 : mentorId = ";
    public static String NO_MENTEE_AREA_BY_USER = "해당 유저는 멘티 설정이 되어있지 않음 : menteeId = ";
    public static String NO_CHATROOM_BY_ROOMID = "해당 ID와 일치하는 채팅방이 없음 : roomId = ";
    public static String NO_MENTORING_BY_MENTORINGID = "해당 ID와 일치하는 멘토링이 없음 : mentoringId = ";

    public static String NOT_CHATROOM_PARTICIPANT = "해당 채팅방의 참여자가 아님 : roomId = ";


    public static String SYSTEM_IO_ERROR_MESSAGE = "서버의 IO 과정에서 문제 발생";


    private final int status;

    ErrorCode(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
