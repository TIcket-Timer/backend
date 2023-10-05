package com.tickettimer.backendserver.dto;

import com.tickettimer.backendserver.domain.musical.SiteCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MusicalNoticeResponse {
    private String id;
    private SiteCategory siteCategory;
    private LocalDateTime openDateTime;
    private String title;
    private String url;
    @Builder
    public MusicalNoticeResponse(
            String id,
            SiteCategory siteCategory,
            LocalDateTime openDateTime,
            String title,
            String url
    ) {
        this.id = id;
        this.siteCategory = siteCategory;
        this.openDateTime = openDateTime;
        this.title = title;
        this.url = url;
    }
}
