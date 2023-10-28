package com.tickettimer.backendserver.repository;

import com.tickettimer.backendserver.domain.musical.Musical;
import com.tickettimer.backendserver.domain.musical.SiteCategory;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.ANY)
@DataJpaTest
@DisplayName("뮤지컬 테스트")
class MusicalRepositoryTest {
    @Autowired
    private MusicalRepository musicalRepository;

    @Test
    @DisplayName("뮤지컬 정보 저장")
    void save() {
        Musical musical = Musical.builder()
                .id("id")
                .title("title")
                .place("place")
                .siteCategory(SiteCategory.INTERPARK)
                .posterUrl("url")
                .runningTime("120분")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .siteLink("link")
                .age("전체연령")
                .price(Arrays.asList("vip : 100000", "s : 80000"))
                .build();

        Musical save = musicalRepository.save(musical);
        assertThat(save.getId()).isEqualTo(musical.getId());
        assertThat(save.getTitle()).isEqualTo(musical.getTitle());
        assertThat(save.getPlace()).isEqualTo(musical.getPlace());
        assertThat(save.getSiteCategory()).isEqualTo(musical.getSiteCategory());
        assertThat(save.getPosterUrl()).isEqualTo(musical.getPosterUrl());
        assertThat(save.getRunningTime()).isEqualTo(musical.getRunningTime());
        assertThat(save.getStartDate()).isEqualTo(musical.getStartDate());
        assertThat(save.getEndDate()).isEqualTo(musical.getEndDate());
        assertThat(save.getSiteLink()).isEqualTo(musical.getSiteLink());

    }

    @Test
    @DisplayName("뮤지컬 사이트와 제목으로 검색")
    void search() {
        Musical musical = Musical.builder()
                .id("id")
                .title("title")
                .place("place")
                .siteCategory(SiteCategory.INTERPARK)
                .posterUrl("url")
                .runningTime("120분")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .siteLink("link")
                .age("전체연령")
                .price(Arrays.asList("vip : 100000", "s : 80000"))
                .build();

        Musical save = musicalRepository.save(musical);
        List<Musical> find = musicalRepository.findByNameAndSiteCategory("title", SiteCategory.INTERPARK.name(), PageRequest.of(0, 1));

        assertThat(find.get(0).getId()).isEqualTo("id");

    }
}