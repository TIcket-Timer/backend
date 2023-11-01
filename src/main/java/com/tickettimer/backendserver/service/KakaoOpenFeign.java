package com.tickettimer.backendserver.service;

import com.tickettimer.backendserver.dto.KakaoLogoutResponse;
import com.tickettimer.backendserver.dto.KakaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name = "KakaoOpenFeign", url = "https://kapi.kakao.com")
public interface KakaoOpenFeign {
    // 카카오 액세스 토큰을 주면 내 정보 받아옴
    @GetMapping(value = "/v2/user/me", headers = "Content-Type=application/x-www-form-urlencoded;charset=utf-8")
    KakaoResponse getMyInfo(@RequestHeader("Authorization") String accessToken);

    // 카카오 액세스 토큰을 주면 로그아웃 (액세스 토큰 만료)
    @PostMapping(value = "/v1/user/logout")
    KakaoLogoutResponse logout(@RequestHeader("Authorization") String accessToken);


}
