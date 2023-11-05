package com.tickettimer.backendserver.global.exception;

public class RefreshTokenNotSameException extends RuntimeException{
    public RefreshTokenNotSameException (){
        super("refresh token 정보가 일치하지 않습니다.");
    }
}
