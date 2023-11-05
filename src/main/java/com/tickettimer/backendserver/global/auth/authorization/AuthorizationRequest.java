package com.tickettimer.backendserver.global.auth.authorization;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthorizationRequest {
    private TokenType type;
    private String token;

    @Builder
    public AuthorizationRequest(TokenType type, String token) {
        this.token=token;
        this.type=type;
    }
    public AuthorizationRequest() {

    }
}
