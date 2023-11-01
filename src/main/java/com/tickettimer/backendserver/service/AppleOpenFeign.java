package com.tickettimer.backendserver.service;

import com.tickettimer.backendserver.dto.AppleLogoutRequest;
import com.tickettimer.backendserver.dto.ApplePublicKeys;
import com.tickettimer.backendserver.dto.KakaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "AppleOpenFeign", url = "https://appleid.apple.com/auth")

public interface AppleOpenFeign {
    @GetMapping("/keys")
    ApplePublicKeys getKeys();

    @PostMapping(value = "/revoke", headers="Content-Type=application/x-www-form-urlencoded;charset=utf-8")
    void expireToken(@RequestBody AppleLogoutRequest appleLogoutRequest);
}
