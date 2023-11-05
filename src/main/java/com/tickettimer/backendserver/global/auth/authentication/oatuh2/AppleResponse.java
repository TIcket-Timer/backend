package com.tickettimer.backendserver.global.auth.authentication.oatuh2;

import lombok.Getter;

@Getter
public class AppleResponse {
    private String sub;
    private String email;
}
