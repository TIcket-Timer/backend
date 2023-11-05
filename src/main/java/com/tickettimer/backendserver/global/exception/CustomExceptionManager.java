package com.tickettimer.backendserver.global.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tickettimer.backendserver.global.dto.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class CustomExceptionManager {
    ObjectMapper mapper = new ObjectMapper();
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultResponse> requestException(MethodArgumentNotValidException ex, BindingResult bindingResult) throws JsonProcessingException, NoSuchFieldException, IllegalAccessException {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError error : allErrors) {
            System.out.println("error = " + error);
            System.out.println("error = " + error.getArguments());
        }
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(bindingResult.getAllErrors().get(0).getDefaultMessage())
                .build();

        return new ResponseEntity<>(res, HttpStatus.valueOf(res.getCode()));
    }
    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<ResultResponse> notFoundException(CustomNotFoundException ex) {
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }
    @ExceptionHandler(FCMException.class)
    public ResponseEntity<ResultResponse> fcmException(FCMException ex) {
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ResultResponse> invalidTokenException(InvalidTokenException ex) {
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }

    @ExceptionHandler(RefreshTokenNotSameException.class)
    public ResponseEntity<ResultResponse> tokenException(RefreshTokenNotSameException ex) {
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }



}
