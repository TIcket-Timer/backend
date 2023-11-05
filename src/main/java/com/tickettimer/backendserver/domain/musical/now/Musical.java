package com.tickettimer.backendserver.domain.musical.now;

import com.tickettimer.backendserver.global.BaseTime;
import com.tickettimer.backendserver.domain.musical.SiteCategory;
import com.tickettimer.backendserver.domain.actor.Actor;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table
public class Musical extends BaseTime {

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
    private String age;
    @Convert(converter = ListStringConverter.class)
    private List<String> price;

    @OneToMany(mappedBy = "musical")
    private List<Actor> actors;

    @Builder
    public Musical(String id, SiteCategory siteCategory, String title, String posterUrl, LocalDate startDate,
                   LocalDate endDate, String place, String runningTime, String siteLink, String age, List<String> price) {
        this.id = id;
        this.siteCategory = siteCategory;
        this.title = title;
        this.posterUrl = posterUrl;
        this.startDate = startDate;
        this.endDate = endDate;
        this.place = place;
        this.runningTime = runningTime;
        this.siteLink = siteLink;
        this.age = age;
        this.price = price;
    }

}
