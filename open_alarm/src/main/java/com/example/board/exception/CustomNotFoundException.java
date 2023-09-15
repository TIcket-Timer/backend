package com.example.board.exception;

import lombok.Getter;

@Getter
public class CustomNotFoundException extends RuntimeException {
    // Not Found : memberId=abc123
    public CustomNotFoundException(String field, String value) {
        super("Not Found : ".concat(field).concat("=").concat(value));
    }
}
