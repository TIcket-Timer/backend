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
    @Column(name = "MUSICAL_ID")
    private String id;

    @Enumerated(EnumType.STRING)
    private SiteCategory siteCategory;
    @OneToMany(mappedBy = "musical")
    private List<MemberMusical> memberMusicals = new ArrayList<>();

    private String title;

    private String posterUrl;

    private LocalDate startDate;

    private LocalDate endDate;

    private String place;

    private String actors;

    private String runningTime;

    private String siteLink;

    @Builder
    public Musical(String id, SiteCategory siteCategory, String title, String posterUrl, LocalDate startDate,
                   LocalDate endDate, String place, String actors, String runningTime, String siteLink) {
        this.id = id;
        this.siteCategory = siteCategory;
        this.title = title;
        this.posterUrl = posterUrl;
        this.startDate = startDate;
        this.endDate = endDate;
        this.place = place;
        this.actors = actors;
        this.runningTime = runningTime;
        this.siteLink = siteLink;
    }

}
