package com.tickettimer.backendserver.repository;

import com.tickettimer.backendserver.domain.musical.MusicalNotice;
import com.tickettimer.backendserver.domain.musical.SiteCategory;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
}