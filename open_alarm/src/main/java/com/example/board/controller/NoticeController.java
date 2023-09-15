package com.example.board.controller;

import com.example.board.domain.Notice;
import com.example.board.domain.ResultResponse;
import com.example.board.dto.NoticeRequestDto;
import com.example.board.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Notice")
public class NoticeController {

    private final NoticeService noticeService;

    /**
     * 예매오픈공지 저장
     */
    @PostMapping("/insert")
    public ResponseEntity<ResultResponse> insertNotice(@RequestBody NoticeRequestDto noticeRequestDto) {
        Long musicalId = noticeRequestDto.getMusicalId();
        // Notice 엔티티 생성
        Notice notice = Notice.builder()
                .musicalId(musicalId)
                .openDate(noticeRequestDto.getOpenDate())
                .title(noticeRequestDto.getTitle())
                .url(noticeRequestDto.getUrl())
                .build();

        // Notice 엔티티 저장
        noticeService.saveNotice(notice);

        // 결과 반환
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("저장 완료되었습니다.")
                .result(notice)
                .build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }

    /**
     * 예매오픈공지 전체 불러오기
     */
    @GetMapping("/read")
    public ResponseEntity<ResultResponse> readNotice() {
        String message = "데이터 조회가 완료되었습니다.";
        // 공지 조회
        List<Notice> notice = noticeService.readNotice();

        //공지가 없으면 메세지 변경
        if(notice.isEmpty()) message = "공지사항이 없습니다.";

        // 결과 반환
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message(message)
                .result(notice)
                .build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }

    /**
     * 예매오픈공지 상세 불러오기
     */
    @GetMapping("/read/{musicalId}")
    public ResponseEntity<ResultResponse> readNoticeMusicalId(@PathVariable Long musicalId) {
        // 공지 조회
        Notice notice = noticeService.readNoticeMusicalId(musicalId);

        // 결과 반환
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("데이터 조회가 완료되었습니다.")
                .result(notice)
                .build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }

    /**
     * 예매오픈공지 삭제
     */
    @PostMapping("/delete")
    public ResponseEntity<ResultResponse> deleteNotice(@RequestParam Long musicalId) {
        // 공지 삭제
        noticeService.deleteNotice(musicalId);

        // 결과 반환
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("삭제되었습니다.")
                .build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }

}
