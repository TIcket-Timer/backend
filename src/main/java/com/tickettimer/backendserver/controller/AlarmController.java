package com.tickettimer.backendserver.controller;

import com.tickettimer.backendserver.domain.Alarm;
import com.tickettimer.backendserver.domain.Member;
import com.tickettimer.backendserver.domain.musical.Actor;
import com.tickettimer.backendserver.domain.musical.Musical;
import com.tickettimer.backendserver.domain.musical.MusicalNotice;
import com.tickettimer.backendserver.dto.ActorRequestDto;
import com.tickettimer.backendserver.dto.ResultResponse;
import com.tickettimer.backendserver.service.AlarmService;
import com.tickettimer.backendserver.service.JwtService;
import com.tickettimer.backendserver.service.MemberService;
import com.tickettimer.backendserver.service.MusicalNoticeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alarms")
public class AlarmController {
    private final AlarmService alarmService;
    private final MusicalNoticeService musicalNoticeService;
    private final MemberService memberService;
    private final JwtService jwtService;
    @PostMapping
    public ResponseEntity<ResultResponse> postAlarm(
            HttpServletRequest request,
            @RequestParam("musicalNoticeId") String musicalNoticeId
    ) {
        Long id = (Long) request.getAttribute("id");
        System.out.println("id = " + id);
        MusicalNotice musicalNotice = musicalNoticeService.findById(musicalNoticeId);
        Member member = memberService.findById(id);
        Alarm alarm = Alarm.builder()
                .musicalNotice(musicalNotice)
                .member(member)
                .build();

        // Alarm 저장
        Alarm save = alarmService.save(alarm);
        // 결과 반환
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("알람을 등록했습니다.")
                .result(musicalNotice).build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }
    @DeleteMapping
    public ResponseEntity<ResultResponse> deleteAlarm(
            HttpServletRequest request,
            @RequestParam("id") Long alarmId
    ) {
        alarmService.delete(alarmId);
        // 결과 반환
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message(alarmId+ " : 뮤지컬 정보를 삭제했습니다.")
                .build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }
}
