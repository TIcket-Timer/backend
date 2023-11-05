package com.tickettimer.backendserver.domain.member;

import com.tickettimer.backendserver.global.BaseTime;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
