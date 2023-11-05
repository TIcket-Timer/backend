package com.tickettimer.backendserver.global.auth.authentication.oatuh2;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "AppleOpenFeign", url = "https://appleid.apple.com/auth")

public interface AppleOpenFeign {
    @GetMapping("/keys")
    ApplePublicKeys getKeys();

}
