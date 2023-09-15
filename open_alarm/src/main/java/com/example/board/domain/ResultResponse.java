package com.example.board.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResultResponse {
    private int code;
    private String message;
    private Object result;
    @Builder
    public ResultResponse(int code, String message, Object result) {
        this.code=code;
        this.message=message;
        this.result=result;
    }
}
