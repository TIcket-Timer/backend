package com.tickettimer.backendserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
