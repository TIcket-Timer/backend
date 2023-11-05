package com.tickettimer.backendserver.repository;

import com.tickettimer.backendserver.domain.musical.notice.MusicalNotice;
import com.tickettimer.backendserver.domain.musical.SiteCategory;
import com.tickettimer.backendserver.domain.musical.notice.MusicalNoticeRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@Transactional
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.ANY)
@DataJpaTest
@DisplayName("뮤지컬 오픈 공지 테스트")
class MusicalNoticeRepositoryTest {
    @Autowired
    private MusicalNoticeRepository musicalNoticeRepository;
    @Test
    @DisplayName("저장 테스트")
    void saveTest() {
        LocalDateTime now = LocalDateTime.now();
        MusicalNotice musicalNotice = MusicalNotice.builder()
                .id("123")
                .title("title")
                .openDateTime(now)
                .url("url")
                .siteCategory(SiteCategory.INTERPARK).build();

        MusicalNotice save = musicalNoticeRepository.save(musicalNotice);
        assertThat(save.getId()).isEqualTo(musicalNotice.getId());
        assertThat(save.getTitle()).isEqualTo(musicalNotice.getTitle());
        assertThat(save.getOpenDateTime()).isEqualTo(musicalNotice.getOpenDateTime());
        assertThat(save.getUrl()).isEqualTo(musicalNotice.getUrl());
        assertThat(save.getSiteCategory()).isEqualTo(musicalNotice.getSiteCategory());

    }

    @Test
    @DisplayName("최근 등록 조회 테스트")
    void deadlineFindTest() {
        LocalDateTime dateTime1 = LocalDateTime.now().plus(1, ChronoUnit.DAYS);
        MusicalNotice musicalNotice1 = MusicalNotice.builder()
                .id("123")
                .title("title")
                .openDateTime(dateTime1)
                .url("url")
                .siteCategory(SiteCategory.INTERPARK).build();
        LocalDateTime dateTime2 = LocalDateTime.now().plus(2, ChronoUnit.DAYS);

        musicalNoticeRepository.save(musicalNotice1);
        MusicalNotice musicalNotice2 = MusicalNotice.builder()
                .id("1234")
                .title("title")
                .openDateTime(dateTime2)
                .url("url")
                .siteCategory(SiteCategory.INTERPARK).build();

        musicalNoticeRepository.save(musicalNotice2);
        LocalDateTime dateTime3 = LocalDateTime.now().plus(4, ChronoUnit.DAYS);

        MusicalNotice musicalNotice3 = MusicalNotice.builder()
                .id("12345")
                .title("title")
                .openDateTime(dateTime3)
                .url("url")
                .siteCategory(SiteCategory.INTERPARK).build();

        musicalNoticeRepository.save(musicalNotice3);

        Pageable pageable = PageRequest.of(0, 2);

        Page<MusicalNotice> musicalNotices = musicalNoticeRepository.findAllByOrderByCreatedTimeDesc(pageable);
        for (MusicalNotice musicalNotice : musicalNotices) {
            System.out.println("musicalNotice = " + musicalNotice.getCreatedTime());
        }
        List<MusicalNotice> content = musicalNotices.getContent();

        assertThat(content.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("마감 임박 조회 테스트")
    void findByOpenDateTimeAfter() {
        LocalDateTime dateTime1 = LocalDateTime.now().plus(1, ChronoUnit.DAYS);
        MusicalNotice musicalNotice1 = MusicalNotice.builder()
                .id("123")
                .title("title")
                .openDateTime(dateTime1)
                .url("url")
                .siteCategory(SiteCategory.INTERPARK).build();
        LocalDateTime dateTime2 = LocalDateTime.now().plus(2, ChronoUnit.DAYS);

        musicalNoticeRepository.save(musicalNotice1);
        MusicalNotice musicalNotice2 = MusicalNotice.builder()
                .id("1234")
                .title("title")
                .openDateTime(dateTime2)
                .url("url")
                .siteCategory(SiteCategory.INTERPARK).build();

        musicalNoticeRepository.save(musicalNotice2);
        LocalDateTime dateTime3 = LocalDateTime.now().minus(4, ChronoUnit.HOURS);

        MusicalNotice musicalNotice3 = MusicalNotice.builder()
                .id("12345")
                .title("title")
                .openDateTime(dateTime3)
                .url("url")
                .siteCategory(SiteCategory.INTERPARK).build();

        musicalNoticeRepository.save(musicalNotice3);

        Pageable pageable = PageRequest.of(0, 3);

        Page<MusicalNotice> musicalNotices = musicalNoticeRepository.findByOpenDateTimeAfterOrderByOpenDateTime(LocalDateTime.now(), pageable);
        List<MusicalNotice> content = musicalNotices.getContent();
        assertThat(content.size()).isEqualTo(2);
    }
    @Test
    @DisplayName("검색 테스트")
    void findBySearchTest() {
        MusicalNotice musicalNotice1 = MusicalNotice.builder()
                .id("123")
                .title("title")
                .openDateTime(LocalDateTime.now())
                .url("url")
                .siteCategory(SiteCategory.INTERPARK).build();
        musicalNoticeRepository.save(musicalNotice1);
        MusicalNotice musicalNotice2 = MusicalNotice.builder()
                .id("1234")
                .title("hello title")
                .openDateTime(LocalDateTime.now())
                .url("url")
                .siteCategory(SiteCategory.INTERPARK).build();
        musicalNoticeRepository.save(musicalNotice2);
        MusicalNotice musicalNotice3 = MusicalNotice.builder()
                .id("1253")
                .title("titl")
                .openDateTime(LocalDateTime.now())
                .url("url")
                .siteCategory(SiteCategory.INTERPARK).build();
        musicalNoticeRepository.save(musicalNotice3);

        Pageable pageable = PageRequest.of(0, 3);
        Page<MusicalNotice> musicalNotices = musicalNoticeRepository.findBySearch("title", pageable);
        List<MusicalNotice> content = musicalNotices.getContent();
        System.out.println("content = " + content.size());
        assertThat(content.size()).isEqualTo(2);
    }

}