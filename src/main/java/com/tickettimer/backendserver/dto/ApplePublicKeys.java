package com.tickettimer.backendserver.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ApplePublicKeys {
    private List<ApplePublicKey> keys;
    @Getter
    public class ApplePublicKey{
        private String kid;
        private String alg;
    }
}
