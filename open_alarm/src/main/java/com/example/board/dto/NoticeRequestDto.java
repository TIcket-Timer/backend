package com.example.board.dto;

import lombok.Getter;

import java.sql.Date;

@Getter
public class NoticeRequestDto {
    private Long musicalId;
    private Date openDate;
    private String title;
    private String url;
}
