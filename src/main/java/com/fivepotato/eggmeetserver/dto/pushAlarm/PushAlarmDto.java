package com.fivepotato.eggmeetserver.dto.pushAlarm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class PushAlarmDto {

    private boolean validate_only;
    private FcmMessage message;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class FcmMessage {
        private FcmNotification notification;
        private String token;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class FcmNotification {
        private String title;
        private String body;
        private String image;
    }
}
