package com.tickettimer.backendserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tickettimer.backendserver.domain.musical.Musical;
import com.tickettimer.backendserver.domain.musical.MusicalNotice;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name="ALARM")
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
    @Builder
    public Alarm(Member member, MusicalNotice musicalNotice) {
        member.getAlarms().add(this);
        musicalNotice.getAlarms().add(this);
        this.member=member;
        this.musicalNotice=musicalNotice;
    }

}
