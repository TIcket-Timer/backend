package com.tickettimer.backendserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FCMRequestDto {
    private String title;
    private String content;
}