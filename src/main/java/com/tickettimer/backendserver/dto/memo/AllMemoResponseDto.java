package com.tickettimer.backendserver.dto.memo;

import com.tickettimer.backendserver.domain.Memo;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class AllMemoResponseDto implements Serializable {
    private final List<MemoResponseDto> memos;

    public AllMemoResponseDto(List<Memo> memos) {
        this.memos = new ArrayList<>();
        for (Memo memo : memos) {
            this.memos.add(new MemoResponseDto(memo));
        }
    }
}
