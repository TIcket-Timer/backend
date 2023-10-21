package com.tickettimer.backendserver.controller;

import com.tickettimer.backendserver.domain.musical.Musical;
import com.tickettimer.backendserver.domain.musical.MusicalNotice;
import com.tickettimer.backendserver.domain.musical.SiteCategory;
import com.tickettimer.backendserver.dto.MusicalNoticeResponse;
import com.tickettimer.backendserver.dto.ResultResponse;
import com.tickettimer.backendserver.service.MusicalNoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/musicalNotices")
@RequiredArgsConstructor
public class MusicalNoticeController {
    private final MusicalNoticeService musicalNoticeService;

    @PostMapping
    public ResponseEntity<ResultResponse> postMusical(@RequestBody MusicalNotice musicalNotice) {
        MusicalNotice save = musicalNoticeService.save(musicalNotice);
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message(save.getId() + " : 뮤지컬 공지 정보를 저장했습니다.")
                .result(save).build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }

    // 아이디로 뮤지컬 공지 정보 검색
    @GetMapping("/deadline/{id}")
    public ResponseEntity<ResultResponse> findMusicalById(@PathVariable("id") String id) {
        MusicalNotice musicalNotice = musicalNoticeService.findById(id);
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("뮤지컬 공지 정보를 가져왔습니다.")
                .result(musicalNotice).build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }
    // 마감 기한 임박한 뮤지컬 정보
    @GetMapping("/deadline")
    public ResponseEntity<ResultResponse> getMusicalDeadline(Pageable pageable) {
        List<MusicalNotice> musicalNotices = musicalNoticeService.findDeadlineNotice(pageable);
        List<MusicalNoticeResponse> response = musicalNotices.stream().map(
                m -> MusicalNoticeResponse.builder()
                        .id(m.getId())
                        .url(m.getUrl())
                        .imageUrl(m.getImageUrl())
                        .title(m.getTitle())
                        .openDateTime(m.getOpenDateTime())
                        .siteCategory(m.getSiteCategory()).build()
        ).collect(Collectors.toList());
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("마감 기한이 임박한 뮤지컬 정보를 가져왔습니다.")
                .result(response).build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }
    // 최근 등록된 뮤지컬 정보
    @GetMapping("/latest")
    public ResponseEntity<ResultResponse> getMusicalLatest(Pageable pageable) {
        List<MusicalNotice> musicalNotices = musicalNoticeService.findLatestNotice(pageable);
        List<MusicalNoticeResponse> response = musicalNotices.stream().map(
                m -> MusicalNoticeResponse.builder()
                        .id(m.getId())
                        .url(m.getUrl())
                        .imageUrl(m.getImageUrl())
                        .title(m.getTitle())
                        .openDateTime(m.getOpenDateTime())
                        .siteCategory(m.getSiteCategory()).build()
        ).collect(Collectors.toList());
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("최근 등록된 뮤지컬 정보를 가져왔습니다.")
                .result(response).build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }

    // 검색
    @GetMapping("/search")
    public ResponseEntity<ResultResponse> searchMusicalNotices(@RequestParam("q") String name, Pageable pageable) {
        List<MusicalNotice> musicalNotices = musicalNoticeService.findBySearch(name, pageable);
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("뮤지컬 공지 겁색 결과를 가져왔습니다.")
                .result(musicalNotices).build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }
}
