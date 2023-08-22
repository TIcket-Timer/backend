package com.tickettimer.backendserver.domain.musical;

import com.tickettimer.backendserver.domain.Alarm;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "MUSICAL_NOTICE")
public class MusicalNotice {
    @Id
    @Column(name = "MUSICAL_NOTICE_ID")
    private String id;

    @Enumerated(EnumType.STRING)
    private SiteCategory siteCategory;
    private LocalDateTime openDateTime;
    private String title;
    private String url;

    @OneToMany(mappedBy = "musicalNotice")
    private List<Alarm> alarms = new ArrayList<>();

    @Builder
    public MusicalNotice(
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
