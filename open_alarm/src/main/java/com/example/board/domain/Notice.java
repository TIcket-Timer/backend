package com.example.board.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long musicalId;

    private Date openDate;

    private String title;

    private String url;

    @Builder
    public Notice(Long musicalId, Date openDate, String title, String url) {
        this.musicalId = musicalId;
        this.openDate = openDate;
        this.title = title;
        this.url = url;
    }
}
