package com.tickettimer.backendserver.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public enum ResourceType {
    KAKAO("kakao"),
    APPLE("apple");
    private String name;

}
