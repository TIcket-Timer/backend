package com.tickettimer.backendserver.domain.fcm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FCMRequestDto {
    private String title;
    private String content;
}