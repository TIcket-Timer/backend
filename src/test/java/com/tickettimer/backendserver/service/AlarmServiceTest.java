package com.tickettimer.backendserver.service;

import com.tickettimer.backendserver.domain.Member;
import com.tickettimer.backendserver.domain.Alarm;
import com.tickettimer.backendserver.domain.musical.Musical;
import com.tickettimer.backendserver.domain.musical.SiteCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class AlarmServiceTest {
//    @Autowired
//    AlarmService alarmService;
//    @Autowired
//    MemberService memberService;
//    @Autowired
//    MusicalService musicalService;
//
//
//    @Test
//    public void save() {
//        Member member1 = Member.builder()
//                .serverId("aa1")
//                .nickname("aa2")
//                .email("aa3")
//                .profileUrl("aa4")
//                .password("aa5")
//                .build();
//        memberService.save(member1);
//
//        Musical musical1 = Musical.builder()
//                .id("11111")
//                .siteLink("link")
//                .place("place")
//                .endDate(LocalDate.now())
//                .startDate(LocalDate.now())
//                .title("title1")
//                .posterUrl("urlurl")
//                .runningTime("80min")
//                .siteCategory(SiteCategory.INTERPARK)
//                .build();
//        musicalService.save(musical1);
//
//        Alarm mm1 = Alarm.builder()
//                .member(member1)
//                .musical(musical1)
//                .build();
//        alarmService.save(mm1);
//
//        List<Alarm> mml  = alarmService.findByMember(member1);
//        for (Alarm alarm : mml) {
//            System.out.println(alarm.getMember().getNickname() + alarm.getMusical().getActors());
//        }
//    }
}