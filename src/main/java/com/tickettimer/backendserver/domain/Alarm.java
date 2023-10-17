package com.tickettimer.backendserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tickettimer.backendserver.converter.AlarmTimeConverter;
import com.tickettimer.backendserver.domain.musical.Musical;
import com.tickettimer.backendserver.domain.musical.MusicalNotice;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ALARM_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "MUSICAL_NOTICE_ID")
    private MusicalNotice musicalNotice;

    @Convert(converter = AlarmTimeConverter.class)
    private List<Integer> alarmTimes = new ArrayList<>(); // 알람 맞춰둔 시간 ex) 5분전, 10분전 ...
    @Builder
    public Alarm(Member member, MusicalNotice musicalNotice, List<Integer> alarmTimes) {
        member.getAlarms().add(this);
        musicalNotice.getAlarms().add(this);
        this.member=member;
        this.musicalNotice=musicalNotice;
        this.alarmTimes = alarmTimes;
    }

}
