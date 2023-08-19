package com.tickettimer.backendserver.controller;

import com.google.firebase.messaging.*;
import com.tickettimer.backendserver.dto.FCMRequestDto;
import com.tickettimer.backendserver.service.FCMService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FCMMessageController {
    private final FCMService fcmService;
    @PostMapping("/fcm")
    public String fcm(@RequestBody FCMRequestDto requestDto) {
        String registrationToken = "메시지를 보낼 기기 토큰";
        Notification notification = Notification.builder()
                .setTitle(requestDto.getTitle())
                .setBody(requestDto.getContent()).build();

        BatchResponse response = fcmService.sendMessageToiAll(notification);
        System.out.println("response = " + response);
        return "success";
    }
}