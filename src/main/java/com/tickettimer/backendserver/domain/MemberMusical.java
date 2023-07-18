package com.tickettimer.backendserver.domain;

import com.tickettimer.backendserver.domain.musical.Musical;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name="ALARM")
public class MemberMusical {

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
    public MemberMusical(Member member, Musical musical) {
        member.getMemberMusicals().add(this);
        musical.getMemberMusicals().add(this);
        this.member=member;
        this.musical=musical;
    }

}
