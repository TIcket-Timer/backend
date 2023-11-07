package com.tickettimer.backendserver.domain.fcm;

import com.google.firebase.messaging.*;
import com.tickettimer.backendserver.domain.member.Member;
import com.tickettimer.backendserver.domain.musical.SiteCategory;
import com.tickettimer.backendserver.global.dto.ResultResponse;
import com.tickettimer.backendserver.domain.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FCMMessageController {
    private final FCMService fcmService;
    private final MemberService memberService;
//    @PostMapping("/fcm")
//    public ResponseEntity<ResultResponse> fcm(@RequestBody FCMRequestDto requestDto) {
//        Notification notification = Notification.builder()
//                .setTitle(requestDto.getTitle())
//                .setBody(requestDto.getContent()).build();
//        // 모두에게 알림 메시지 보냄
//        BatchResponse response = fcmService.sendMessageToAll(notification);
//        log.info(response.toString());
//        ResultResponse res = ResultResponse.builder()
//                .code(HttpStatus.OK.value())
//                .message("모두에게 알림 메시지를 보냈습니다.")
//                .result(response).build();
//        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
//    }
    @PostMapping("/fcm/{site}")
    public ResponseEntity<ResultResponse> sendFCMWithSite(
            @PathVariable("site") String site,
            @RequestBody FCMRequestDto requestDto
    ) {
        SiteCategory siteCategory = SiteCategory.valueOf(site.toUpperCase());

        // 사이트 알람 설정한 사람한테만 메시지 보냄
        List<Member> members = memberService.findMemberBySiteAlarm(siteCategory);
        Notification notification = Notification.builder()
                .setTitle(requestDto.getTitle())
                .setBody(requestDto.getContent())
                .build();
        BatchResponse response = fcmService.sendMessages(notification, members);
        log.info(response.toString());
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message(site + "알림 메시지를 보냈습니다.")
                .result(response).build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }



}