package com.tickettimer.backendserver.domain.alarm;

import com.tickettimer.backendserver.domain.musical.notice.MusicalNotice;
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
