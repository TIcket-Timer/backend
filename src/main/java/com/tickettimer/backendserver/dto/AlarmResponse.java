package com.tickettimer.backendserver.dto;

import com.tickettimer.backendserver.domain.musical.MusicalNotice;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AlarmResponse {
    private Long id;
    private MusicalNotice musicalNotice;
    private List<Integer> alarmTimes;

    @Builder
    public AlarmResponse(Long id, MusicalNotice musicalNotice, List<Integer> alarmTimes) {
        this.id = id;
        this.musicalNotice = musicalNotice;
        this.alarmTimes = alarmTimes;
    }

}
