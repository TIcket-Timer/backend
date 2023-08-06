package com.tickettimer.backendserver.controller;

import com.tickettimer.backendserver.domain.musical.Musical;
import com.tickettimer.backendserver.dto.ResultResponse;
import com.tickettimer.backendserver.service.MusicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/musicals")
@RequiredArgsConstructor
public class MusicalController {
    private final MusicalService musicalService;
    @PostMapping("/")
    public ResponseEntity<ResultResponse> postMusical(@RequestBody Musical musical) {
        System.out.println("musical = " + musical.getId());
        Musical save = musicalService.save(musical);
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message(save.getId() + " : 뮤지컬 정보를 저장했습니다.")
                .result(save).build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }
    @GetMapping("/")
    public ResponseEntity<ResultResponse> getAllMusical() {
        List<Musical> musicals = musicalService.findAll();
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("뮤지컬 정보를 저장했습니다.")
                .result(musicals).build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }
}
