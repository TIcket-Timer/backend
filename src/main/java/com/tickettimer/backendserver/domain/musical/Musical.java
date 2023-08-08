package com.tickettimer.backendserver.domain.musical;

import com.tickettimer.backendserver.domain.Member;
import com.tickettimer.backendserver.domain.MemberMusical;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="MUSICAL")
public class Musical {

    @Id
    private String musicalId;

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
    private List<MemberMusical> memberMusicals = new ArrayList<>();

    @OneToMany(mappedBy = "musical")
    private List<Actor> actors;

    @Builder
    public Musical(String musicalId, SiteCategory siteCategory, String title, String posterUrl, LocalDate startDate,
                   LocalDate endDate, String place, String runningTime, String siteLink) {
        this.musicalId = musicalId;
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
