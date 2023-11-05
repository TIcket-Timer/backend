package com.tickettimer.backendserver.repository;

import com.tickettimer.backendserver.domain.alarm.Alarm;
import com.tickettimer.backendserver.domain.member.Member;
import com.tickettimer.backendserver.domain.musical.notice.MusicalNotice;
import com.tickettimer.backendserver.domain.musical.SiteCategory;
import com.tickettimer.backendserver.domain.alarm.AlarmRepository;
import com.tickettimer.backendserver.domain.member.MemberRepository;
import com.tickettimer.backendserver.domain.musical.notice.MusicalNoticeRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.ANY)
@DataJpaTest
@DisplayName("알람 테스트 테스트")
class AlarmRepositoryTest {
    @Autowired
    private AlarmRepository alarmRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MusicalNoticeRepository musicalNoticeRepository;

    @Test
    @DisplayName("알람 저장 테스트")
    void saveTest() {
        Member member = Member.builder()
                .serverId("interpark")
                .nickname("nick")
                .profileUrl("url")
                .email("abc@naver.com")
                .password("123456")
                .build();

        memberRepository.save(member);

        MusicalNotice musicalNotice = MusicalNotice.builder()
                .id("id")
                .title("title")
                .siteCategory(SiteCategory.INTERPARK)
                .url("url")
                .openDateTime(LocalDateTime.now())
                .build();

        musicalNoticeRepository.save(musicalNotice);

        Alarm alarm = Alarm.builder()
                .member(member)
                .musicalNotice(musicalNotice)
                .alarmTimes(List.of(1, 2, 3)).build();

        Alarm save = alarmRepository.save(alarm);

        assertThat(save.getMember()).isEqualTo(member);
        assertThat(save.getMusicalNotice()).isEqualTo(musicalNotice);
    }

    @Test
    @DisplayName("내 알람 찾기 테스트")
    void findTest() {
        Member member = Member.builder()
                .serverId("interpark")
                .nickname("nick")
                .profileUrl("url")
                .email("abc@naver.com")
                .password("123456")
                .build();

        memberRepository.save(member);

        // 뮤지컬 두 개 알람으로 등록
        MusicalNotice musicalNotice1 = MusicalNotice.builder()
                .id("id")
                .title("title")
                .siteCategory(SiteCategory.INTERPARK)
                .url("url")
                .openDateTime(LocalDateTime.now())
                .build();
        MusicalNotice musicalNotice2 = MusicalNotice.builder()
                .id("id2")
                .title("title2")
                .siteCategory(SiteCategory.MELON)
                .url("url2")
                .openDateTime(LocalDateTime.now())
                .build();

        musicalNoticeRepository.save(musicalNotice1);
        musicalNoticeRepository.save(musicalNotice2);
        Alarm alarm1 = Alarm.builder()
                .member(member)
                .musicalNotice(musicalNotice1)
                .alarmTimes(List.of(1, 2, 3)).build();
        Alarm alarm2 = Alarm.builder()
                .member(member)
                .musicalNotice(musicalNotice1)
                .alarmTimes(List.of(1, 2, 3)).build();

        Alarm save1 = alarmRepository.save(alarm1);
        Alarm save2 = alarmRepository.save(alarm2);

        List<Alarm> alarms = alarmRepository.findByMember(member);
        assertThat(alarms.size()).isEqualTo(2);

    }
}