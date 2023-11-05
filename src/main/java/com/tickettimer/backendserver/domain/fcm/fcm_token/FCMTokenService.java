package com.tickettimer.backendserver.domain.fcm.fcm_token;

import com.tickettimer.backendserver.domain.member.FCMToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FCMTokenService {
    private final FCMTokenRepository fcmTokenRepository;
    public FCMToken save(FCMToken fcmToken) {
        return fcmTokenRepository.save(fcmToken);
    }

    public void update(FCMToken fcmToken, String token) {
        fcmToken.updateToken(token);
        fcmTokenRepository.save(fcmToken);
    }
}
