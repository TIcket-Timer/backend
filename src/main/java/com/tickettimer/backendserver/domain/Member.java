package com.tickettimer.backendserver.domain;


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
    private List<MemberMusical> memberMusicals = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Memo> memos = new ArrayList<>();

    private String serverId;

    private String nickname;

    private String email;

    private String profileUrl;

    @Builder
    public Member(String serverId, String nickname, String email, String profileUrl) {
        this.serverId=serverId;
        this.nickname=nickname;
        this.email=email;
        this.profileUrl=profileUrl;
    }

}
