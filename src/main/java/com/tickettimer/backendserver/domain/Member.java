package com.tickettimer.backendserver.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name="MEMBER")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @OneToMany(mappedBy = "member")
    private List<Alarm> alarms = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Memo> memos = new ArrayList<>();

    // fcmToken의 경우 FCMToken 엔티티를 생성할 때 생성
    @OneToOne(mappedBy = "member")
    @Setter
    private FCMToken fcmToken;

    private String serverId;

    private String nickname;

    private String email;

    private String profileUrl;
    private String password;

    @Builder
    public Member(String serverId, String nickname, String email, String profileUrl, String password) {
        this.serverId=serverId;
        this.nickname=nickname;
        this.email=email;
        this.profileUrl=profileUrl;
        this.password=password;
    }

}
