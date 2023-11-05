package com.tickettimer.backendserver.domain.alarm;

import com.tickettimer.backendserver.domain.member.Member;
import com.tickettimer.backendserver.domain.musical.notice.MusicalNotice;
import com.tickettimer.backendserver.global.dto.ResultResponse;
import com.tickettimer.backendserver.global.auth.JwtService;
import com.tickettimer.backendserver.domain.member.MemberService;
import com.tickettimer.backendserver.domain.musical.notice.MusicalNoticeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alarms")
public class AlarmController {
    private final AlarmService alarmService;
    private final MusicalNoticeService musicalNoticeService;
    private final MemberService memberService;
    @GetMapping
    public ResponseEntity<ResultResponse> postAlarm(
            HttpServletRequest request
    ) {
        Long id = (Long) request.getAttribute("id");
        Member member = memberService.findById(id);
        List<Alarm> alarms = member.getAlarms();
        List<AlarmResponse> response = alarms.stream().map(
                m -> AlarmResponse.builder().id(m.getId()).musicalNotice(m.getMusicalNotice()).alarmTimes(m.getAlarmTimes()).build()
        ).collect(Collectors.toList());

        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("등록한 알람을 가져왔습니다.")
                .result(response).build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }
    @PostMapping
    public ResponseEntity<ResultResponse> postAlarm(
            HttpServletRequest request,
            @RequestBody AlarmRequest alarmRequest
    ) {
        Long id = (Long) request.getAttribute("id");
        MusicalNotice musicalNotice = musicalNoticeService.findById(alarmRequest.getMusicalNoticeId());
        Member member = memberService.findById(id);
        Alarm alarm = Alarm.builder()
                .musicalNotice(musicalNotice)
                .member(member)
                .alarmTimes(alarmRequest.getAlarmTimes())
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
    @DeleteMapping("/{id}")
    public ResponseEntity<ResultResponse> deleteAlarm(
            HttpServletRequest request,
            @PathVariable("id") Long alarmId
    ) {
        // 알람을 등록한 사람이 요청한 것인지 확인
        Long id = (Long) request.getAttribute("id");
        Member member = memberService.findById(id);
        Alarm alarm = alarmService.findById(alarmId);

        // 다른 사람이 요청했다면 거부
        if (member != alarm.getMember()) {
            ResultResponse res = ResultResponse.builder()
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .message(alarmId+ " : 알람을 삭제할 권한이 없습니다.")
                    .build();
            return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));

        }
        alarmService.delete(alarmId);
        // 결과 반환
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message(alarmId+ " : 알람을 삭제했습니다.")
                .result(alarm.getMusicalNotice())
                .build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }
}
