package com.tickettimer.backendserver.domain.member;


import com.tickettimer.backendserver.domain.memo.Memo;
import com.tickettimer.backendserver.domain.alarm.Alarm;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alarm> alarms = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Memo> memos = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    private FCMToken fcmToken;

    private String serverId;
    @Setter
    private String nickname;

    private String email;

    private String password;
    private String role;
    @Setter
    private boolean interAlarm;
    @Setter
    private boolean melonAlarm;
    @Setter
    private boolean yesAlarm;

    @Builder
    public Member(String serverId, String nickname, String email, String password, String role) {
        this.serverId=serverId;
        this.nickname=nickname;
        this.email=email;
        this.password = password;
        this.interAlarm = true;
        this.melonAlarm = true;
        this.yesAlarm = true;
        this.role = role;
    }

}
