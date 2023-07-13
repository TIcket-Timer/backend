package com.tickettimer.backendserver.domain.musical;

import com.tickettimer.backendserver.domain.MemberMusical;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="MUSICAL")
public class Musical {

    @Id
    @Column(name = "MUSICAL_ID")
    private String id;

    @Enumerated(EnumType.STRING)
    private SiteCategory siteCategory;

    private String title;

    private String posterUrl;

    private LocalDate startDate;

    private LocalDate endDate;

    private String place;

    private String actors;

    private String runningTime;

    private String siteLink;

}