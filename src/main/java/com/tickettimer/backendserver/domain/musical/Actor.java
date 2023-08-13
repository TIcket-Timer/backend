package com.tickettimer.backendserver.domain.musical;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "ACTOR")
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
