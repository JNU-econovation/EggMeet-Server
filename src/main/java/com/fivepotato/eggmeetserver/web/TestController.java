package com.fivepotato.eggmeetserver.web;

import com.fivepotato.eggmeetserver.service.FirebaseCloudMessagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private FirebaseCloudMessagingService firebaseCloudMessagingService;

    @GetMapping("/")
    public String greet() {
        firebaseCloudMessagingService.sendPushAlarmTo();
        return "hello";
    }
}
