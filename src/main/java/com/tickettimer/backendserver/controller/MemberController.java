package com.tickettimer.backendserver.controller;

import com.tickettimer.backendserver.domain.Member;
import com.tickettimer.backendserver.domain.musical.SiteCategory;
import com.tickettimer.backendserver.dto.*;
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
        MemberInfo memberInfo = new MemberInfo(member.getNickname(), member.getEmail());
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

    @GetMapping("/alarms")
    public ResponseEntity<ResultResponse> getFcmAlarm(
            HttpServletRequest request
    ) {

        Long id = (Long) request.getAttribute("id");
        Member member = memberService.findById(id);
        MemberAlarmResponse alarm = MemberAlarmResponse.builder()
                .interAlarm(member.isInterAlarm())
                .yesAlarm(member.isYesAlarm())
                .melonAlarm(member.isMelonAlarm()).build();

        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("사이트별 알람 설정 정보를 가져왔습니다.:")
                .result(alarm).build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));

    }

    @PatchMapping("/nickname")
    public ResponseEntity<ResultResponse> updateNickname(
            HttpServletRequest request,
            @RequestBody NicknameChange nicknameChange
    ) {
        Long id = (Long) request.getAttribute("id");
        memberService.updateNickname(id, nicknameChange.getName());
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("별명을 변경했습니다.")
                .result(nicknameChange.getName()).build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));

    }

    @DeleteMapping
    public ResponseEntity<ResultResponse> deleteMember(
            HttpServletRequest request
    ) {
        Long id = (Long) request.getAttribute("id");
        Member member = memberService.findById(id);
        memberService.delete(member);
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("탈퇴가 완료되었습니다.")
                .build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));

    }

}
