package com.tickettimer.backendserver.domain;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash(value = "token", timeToLive = 30)
@AllArgsConstructor
@Getter
public class Token {
    @Id
    private Long id;
    private String refreshToken;
}