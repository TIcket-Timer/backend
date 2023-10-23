package com.tickettimer.backendserver.controller;

import com.tickettimer.backendserver.domain.Member;
import com.tickettimer.backendserver.domain.musical.SiteCategory;
import com.tickettimer.backendserver.dto.FcmAlarmDto;
import com.tickettimer.backendserver.dto.MemberInfo;
import com.tickettimer.backendserver.dto.ResultResponse;
import com.tickettimer.backendserver.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @GetMapping
    public ResponseEntity<ResultResponse> findMyInfo(
            HttpServletRequest request
    ) {
        Long id = (Long) request.getAttribute("id");
        Member member = memberService.findById(id);
        MemberInfo memberInfo = new MemberInfo(member.getNickname());
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("내 정보를 가져왔습니다.")
                .result(memberInfo).build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));

    }

    @PatchMapping("/alarms")
    public ResponseEntity<ResultResponse> setFcmAlarm(
            HttpServletRequest request,
            @RequestBody FcmAlarmDto fcmAlarmDto
    ) {

        Long id = (Long) request.getAttribute("id");
        SiteCategory siteCategory = SiteCategory.valueOf(fcmAlarmDto.getSite().toUpperCase());
        memberService.updateFcmAlarm(id, siteCategory, fcmAlarmDto.isBool());
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("FCM 알람을 설정했습니다.")
                .result(fcmAlarmDto).build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));

    }

    @PatchMapping("/nickname")
    public ResponseEntity<ResultResponse> updateNickname(
            HttpServletRequest request,
            @RequestBody String name
    ) {
        Long id = (Long) request.getAttribute("id");
        memberService.updateNickname(id, name);
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("별명을 변경했습니다.")
                .result(name).build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));

    }

}