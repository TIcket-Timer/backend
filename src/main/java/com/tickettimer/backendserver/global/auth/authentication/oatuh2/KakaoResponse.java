package com.tickettimer.backendserver.global.auth.authentication.oatuh2;

import lombok.Getter;

@Getter
public class KakaoResponse {
    private Long id;
    private KakaoAccount kakao_account;
    @Getter
    public
    class KakaoAccount{
        private String email;
    }
}
