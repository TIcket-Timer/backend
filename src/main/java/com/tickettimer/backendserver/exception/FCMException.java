package com.tickettimer.backendserver.exception;

public class FCMException extends RuntimeException {
    public FCMException(Throwable cause) {
        super("FCM 오류가 발생했습니다.", cause);
    }
}
