package com.tickettimer.backendserver.domain.musical;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SiteCategory {

    INTERPARK("interpark"),
    YES24("yes24"),
    MELON("melon");

    private String name;

}
