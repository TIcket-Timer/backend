package com.tickettimer.backendserver.domain.actor;

import lombok.Getter;

@Getter
public class ActorRequestDto {
    private String musicalId;
    private String actorName;
    private String roleName;
    private String profileUrl;
}
