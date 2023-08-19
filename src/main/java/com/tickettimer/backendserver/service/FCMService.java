package com.tickettimer.backendserver.service;

import com.google.firebase.messaging.*;
import com.tickettimer.backendserver.domain.FCMToken;
import com.tickettimer.backendserver.exception.FCMException;
import com.tickettimer.backendserver.repository.FCMTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FCMService {
    private final FCMTokenRepository fcmTokenRepository;
    /**
     * 알람 기능이 켜져있는 사람들에게 모두 알람을 보냄.
     * FirebaseMessagingException이 발생하면 런타임 예외로 변경 후 던짐.
     */
    public BatchResponse sendMessageToiAll(Notification notification) {
        List<FCMToken> fcmTokens = fcmTokenRepository.findByIsActivated(true);
        List<String> tokens = fcmTokens.stream().map(
                t -> t.getToken()
        ).collect(Collectors.toList());
        MulticastMessage message = MulticastMessage.builder()
                .addAllTokens(tokens)
                .setNotification(notification)
                .build();
        try {
            BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
            return response;

        } catch (FirebaseMessagingException ex) {
            throw new FCMException(ex);
        }
    }
}
