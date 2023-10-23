package com.tickettimer.backendserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FcmAlarmDto {
    private String site;
    private boolean bool;
}
