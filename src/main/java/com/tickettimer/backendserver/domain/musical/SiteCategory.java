package com.tickettimer.backendserver.domain.musical;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SiteCategory {

    INTERPARK("INTERPARK"),
    YES24("YES24"),
    MELON("MELON");

    private String name;

    @JsonCreator
    public static SiteCategory from(String val) {
        return SiteCategory.valueOf(val.toUpperCase());
    }

}
