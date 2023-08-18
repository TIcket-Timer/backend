package com.tickettimer.backendserver.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class FCMToken extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Member member;
    private String token;
    @Builder
    public FCMToken(Member member, String token) {
        this.member=member;
        this.token=token;
        member.setFcmToken(this);

    }
    public void updateToken(String token) {
        this.token = token;
    }
}
