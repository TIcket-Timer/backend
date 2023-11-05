package com.tickettimer.backendserver.domain.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FcmAlarmDto {
    private String site;
    private boolean bool;
}
