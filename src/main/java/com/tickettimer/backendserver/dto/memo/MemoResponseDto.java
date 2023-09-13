package com.tickettimer.backendserver.dto.memo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tickettimer.backendserver.domain.Member;
import com.tickettimer.backendserver.domain.Memo;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
public class MemoResponseDto implements Serializable {

    private final Long id;

    private final LocalDate date;

    private final String content;

    public MemoResponseDto(Memo memo) {
        this.id = memo.getId();
        this.content = memo.getContent();
        this.date = memo.getDate();
    }
}
