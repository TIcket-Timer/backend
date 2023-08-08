package com.tickettimer.backendserver.domain.musical;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long actorId;
    private String actorName;
    private String profileUrl;
    private String roleName;
    @ManyToOne
    private Musical musical;

    @Builder
    public Actor(String actorName, String profileUrl, String roleName, Musical musical) {
        this.actorName = actorName;
        this.profileUrl = profileUrl;
        this.roleName = roleName;
        this.musical = musical;
    }
}
