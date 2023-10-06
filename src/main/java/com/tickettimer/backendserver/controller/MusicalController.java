package com.tickettimer.backendserver.controller;

import com.tickettimer.backendserver.domain.musical.Musical;
import com.tickettimer.backendserver.domain.musical.MusicalNotice;
import com.tickettimer.backendserver.domain.musical.SiteCategory;
import com.tickettimer.backendserver.dto.MusicalNoticeResponse;
import com.tickettimer.backendserver.dto.MusicalResponse;
import com.tickettimer.backendserver.dto.ResultResponse;
import com.tickettimer.backendserver.service.MusicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/musicals")
@RequiredArgsConstructor
public class MusicalController {
    private final MusicalService musicalService;

    @PostMapping
    public ResponseEntity<ResultResponse> postMusical(@RequestBody Musical musical) {
        System.out.println("musical = " + musical.getId());
        Musical save = musicalService.save(musical);
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message(save.getId() + " : 뮤지컬 정보를 저장했습니다.")
                .result(save).build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }

//    @GetMapping
//    public ResponseEntity<ResultResponse> findAllMusical() {
//        List<Musical> musicals = musicalService.findAll();
//        ResultResponse res = ResultResponse.builder()
//                .code(HttpStatus.OK.value())
//                .message("모든 뮤지컬 정보를 가져왔습니다.")
//                .result(musicals).build();
//        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
//    }
    @GetMapping
    public ResponseEntity<ResultResponse> findAllMusicalPagination(Pageable pageable) {
        List<Musical> musicals = musicalService.findAllPagination(pageable);
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("모든 뮤지컬 정보를 가져왔습니다.")
                .result(musicals).build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }

    @GetMapping("/site/{site}")
    public ResponseEntity<ResultResponse> findMusicalBySiteCategory(@PathVariable("site") String site, Pageable pageable) {
        SiteCategory siteCategory = SiteCategory.valueOf(site.toUpperCase());
        List<Musical> musicals = musicalService.findBySiteCategory(siteCategory, pageable);
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("뮤지컬 정보를 가져왔습니다.")
                .result(musicals).build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }
    // 최근 등록된 뮤지컬 정보
    @GetMapping("/latest")
    public ResponseEntity<ResultResponse> getMusicalLatest(Pageable pageable) {
        List<Musical> musicals = musicalService.findLatestNotice(pageable);
//        List<MusicalResponse> response = musicals.stream().map(
//                m -> MusicalResponse.builder()
//                        .id(m.getId())
//                        .siteCategory(m.getSiteCategory())
//                        .place(m.getPlace())
//                        .title(m.getTitle())
//                        .startDate(m.getStartDate())
//                        .endDate(m.getEndDate())
//                        .runningTime(m.getRunningTime())
//                        .posterUrl(m.getPosterUrl())
//                        .siteLink(m.getSiteLink())
//                        .build()
//        ).collect(Collectors.toList());
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("최근 등록된 뮤지컬 정보를 가져왔습니다.")
                .result(musicals).build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResultResponse> findMusicalById(@PathVariable("id") String id) {
        Musical musical = musicalService.findById(id);
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("뮤지컬 정보를 가져왔습니다.")
                .result(musical).build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }

    @GetMapping("/search")
    public ResponseEntity<ResultResponse> searchMusicalByName(@RequestParam("q") String name, Pageable pageable) {
        List<Musical> musicals = musicalService.findByName(name, pageable);
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("뮤지컬 겁색 결과를 가져왔습니다.")
                .result(musicals).build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }
}
