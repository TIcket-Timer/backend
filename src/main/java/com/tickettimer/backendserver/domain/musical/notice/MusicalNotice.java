package com.tickettimer.backendserver.domain.musical.notice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tickettimer.backendserver.domain.musical.SiteCategory;
import com.tickettimer.backendserver.domain.alarm.Alarm;
import com.tickettimer.backendserver.global.BaseTime;
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
@Table
public class MusicalNotice extends BaseTime {
    @Id
    @Column(name = "MUSICAL_NOTICE_ID")
    private String id;

    @Enumerated(EnumType.STRING)
    private SiteCategory siteCategory;
    private LocalDateTime openDateTime;
    private String title;
    private String url;
//    private String content;
//    private String imageUrl;


    @OneToMany(mappedBy = "musicalNotice")
    @JsonIgnore
    private List<Alarm> alarms = new ArrayList<>();

    @Builder
    public MusicalNotice(
            String id,
            SiteCategory siteCategory,
            LocalDateTime openDateTime,
            String title,
            String url
//            String imageUrl,
//            String content
    ) {
        this.id = id;
        this.siteCategory = siteCategory;
        this.openDateTime = openDateTime;
        this.title = title;
        this.url = url;
//        this.imageUrl = imageUrl;
//        this.content = content;
    }
}
