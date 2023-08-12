package com.tickettimer.backendserver.domain;

import com.tickettimer.backendserver.domain.musical.Musical;
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
    @JoinColumn(name = "MUSICAL_ID")
    private Musical musical;
    @Builder
    public Alarm(Member member, Musical musical) {
        member.getAlarms().add(this);
        musical.getAlarms().add(this);
        this.member=member;
        this.musical=musical;
    }

}
