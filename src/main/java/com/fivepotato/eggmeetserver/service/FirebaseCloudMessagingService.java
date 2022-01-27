package com.fivepotato.eggmeetserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fivepotato.eggmeetserver.dto.pushAlarm.PushAlarmDto;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.net.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class FirebaseCloudMessagingService {

    private final String CLOUD_MESSAGING_API_URL = "https://www.googleapis.com/auth/cloud-platform";
    private final String FIREBASE_CONFIG_PATH = "firebase/firebase_service_key.json";
    private final String CLOUD_MESSAGING_END_POINT = "https://fcm.googleapis.com/v1/projects/eggmeet-4fe62/message:send";
    private final String BODY_MEDIA_TYPE = "application/json; charset=utf-8";
    private final String REQUEST_AUTHORIZATION_HEADER = "Bearer ";
    private final String REQUEST_CONTENT_TYPE = "application/json; UTF-8";

    private final ObjectMapper objectMapper;

    public void sendPushAlarmTo(String targetToken, String title, String body) throws IOException {
        String message = makePushAlarm(targetToken, title, body);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get(BODY_MEDIA_TYPE));
        Request request = new Request.Builder()
                .url(CLOUD_MESSAGING_END_POINT)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, REQUEST_AUTHORIZATION_HEADER + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, REQUEST_CONTENT_TYPE)
                .build();

        Response response = client.newCall(request).execute();

        log.info("[PUSHALARM] : " + response.body().string());
    }

    private String makePushAlarm(String targetToken, String title, String body) throws JsonProcessingException {
        PushAlarmDto pushAlarm = PushAlarmDto.builder()
                .message(
                        PushAlarmDto.FcmMessage.builder()
                                .token(targetToken)
                                .notification(
                                        PushAlarmDto.FcmNotification.builder()
                                                .title(title)
                                                .body(body)
                                                .image(null)
                                                .build()
                                )
                                .build()
                )
                .validate_only(false)
                .build();

        return objectMapper.writeValueAsString(pushAlarm);
    }

    private String getAccessToken() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ClassPathResource(FIREBASE_CONFIG_PATH)
                        .getInputStream()).createScoped(List.of(CLOUD_MESSAGING_API_URL));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}
