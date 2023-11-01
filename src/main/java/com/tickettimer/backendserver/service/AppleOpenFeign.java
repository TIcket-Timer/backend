package com.tickettimer.backendserver.service;

import com.tickettimer.backendserver.dto.KakaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "AppleOpenFeign", url = "https://appleid.apple.com/auth/keys")

public interface AppleOpenFeign {
    @GetMapping(headers = "Content-Type=application/x-www-form-urlencoded;charset=utf-8")
    KakaoResponse getMyInfo(@RequestHeader("Authorization") String accessToken);
}
