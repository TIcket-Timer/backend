package com.tickettimer.backendserver.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AlarmRequest {
    private String musicalNoticeId;
    private List<Integer> alarmTimes;
}
