package com.tickettimer.backendserver.domain.musical.now;

import com.tickettimer.backendserver.domain.actor.Actor;
import com.tickettimer.backendserver.domain.musical.SiteCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class MusicalResponse {
    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private SiteCategory siteCategory;

    private String title;

    private String posterUrl;

    private LocalDate startDate;

    private LocalDate endDate;

    private String place;

    private String runningTime;

    private String siteLink;

    @OneToMany(mappedBy = "musical")
    private List<Actor> actors;

    @Builder
    public MusicalResponse(String id, SiteCategory siteCategory, String title, String posterUrl, LocalDate startDate,
                   LocalDate endDate, String place, String runningTime, String siteLink) {
        this.id = id;
        this.siteCategory = siteCategory;
        this.title = title;
        this.posterUrl = posterUrl;
        this.startDate = startDate;
        this.endDate = endDate;
        this.place = place;
        this.runningTime = runningTime;
        this.siteLink = siteLink;
    }
}