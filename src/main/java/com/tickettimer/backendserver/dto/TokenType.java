package com.tickettimer.backendserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public enum TokenType {
    ACCESS("accessToken"),
    REFRESH("refreshToken");
    private String name;

}
