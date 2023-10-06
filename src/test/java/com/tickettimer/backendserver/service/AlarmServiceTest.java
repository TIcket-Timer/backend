package com.tickettimer.backendserver.service;

import com.tickettimer.backendserver.domain.Member;
import com.tickettimer.backendserver.domain.Alarm;
import com.tickettimer.backendserver.domain.musical.Musical;
import com.tickettimer.backendserver.domain.musical.MusicalNotice;
import com.tickettimer.backendserver.domain.musical.SiteCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class AlarmServiceTest {
    @Autowired
    AlarmService alarmService;
    @Autowired
    MemberService memberService;
    @Autowired
    MusicalNoticeService musicalNoticeService;


    @Test
    public void save() {

        Member member1 = Member.builder()
                .serverId("aa1")
                .nickname("aa2")
                .email("aa3")
                .profileUrl("aa4")
                .password("aa5")
                .build();
        memberService.save(member1);

        MusicalNotice musicalNotice1 = MusicalNotice.builder().openDateTime(LocalDateTime.now())
                .id("id")
                .url("url")
                .siteCategory(SiteCategory.INTERPARK)
                .title("title")
                .build();
        musicalNoticeService.save(musicalNotice1);

        Alarm alarm = Alarm.builder()
                .member(member1)
                .musicalNotice(musicalNotice1)
                .build();
        alarmService.save(alarm);

        List<Alarm> mml  = alarmService.findByMember(member1);
    }
}