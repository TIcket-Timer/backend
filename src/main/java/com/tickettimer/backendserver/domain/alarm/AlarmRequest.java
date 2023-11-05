package com.tickettimer.backendserver.domain.alarm;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AlarmRequest {
    private String musicalNoticeId;
    private List<Integer> alarmTimes;
}
