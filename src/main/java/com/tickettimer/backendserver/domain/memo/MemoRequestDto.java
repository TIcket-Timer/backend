package com.tickettimer.backendserver.domain.memo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemoRequestDto {
    private String content;

    @JsonDeserialize
    private LocalDate date;
}
