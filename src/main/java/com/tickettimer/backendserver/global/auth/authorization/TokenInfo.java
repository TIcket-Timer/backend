package com.tickettimer.backendserver.global.auth.authorization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenInfo {
    private String accessToken;
    private String refreshToken;

    @Builder
    public TokenInfo(String accessToken, String refreshToken) {
        this.accessToken=accessToken;
        this.refreshToken=refreshToken;
    }
}
