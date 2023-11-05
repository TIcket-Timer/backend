package com.tickettimer.backendserver.domain.actor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tickettimer.backendserver.domain.musical.now.Musical;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long actorId;
    private String actorName;
    private String profileUrl;
    private String roleName;
    @ManyToOne
    @JsonIgnore
    private Musical musical;

    @Builder
    public Actor(String actorName, String profileUrl, String roleName, Musical musical) {
        this.actorName = actorName;
        this.profileUrl = profileUrl;
        this.roleName = roleName;
        this.musical = musical;
    }
}
