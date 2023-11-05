package com.tickettimer.backendserver.domain.memo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tickettimer.backendserver.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMO_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(columnDefinition = "DATE")
    @JsonFormat(pattern = "yyyy-mm-dd")
    private LocalDate date;

    private String content;

    @Builder
    public Memo(Member member, LocalDate date, String content) {
        this.member = member;
        this.date = date;
        this.content = content;
        member.getMemos().add(this);
    }

}
