package com.tickettimer.backendserver.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="MEMO")
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMO_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private LocalDate createdAt;

    private String content;

    @Builder
    public Memo(Member member, LocalDate createdAt, String content) {
        this.member=member;
        this.createdAt=createdAt;
        this.content=content;
        member.getMemos().add(this);
    }

}
