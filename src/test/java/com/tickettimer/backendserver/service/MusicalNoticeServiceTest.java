package com.tickettimer.backendserver.service;

import com.tickettimer.backendserver.domain.musical.notice.MusicalNotice;
import com.tickettimer.backendserver.domain.musical.SiteCategory;
import com.tickettimer.backendserver.domain.musical.notice.MusicalNoticeService;
import com.tickettimer.backendserver.global.exception.CustomNotFoundException;
import com.tickettimer.backendserver.domain.musical.notice.MusicalNoticeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("뮤지컬 공지")
class MusicalNoticeServiceTest {
    @Mock
    private MusicalNoticeRepository musicalNoticeRepository;
    @InjectMocks
    private MusicalNoticeService musicalNoticeService;
    @Test
    @DisplayName("저장")
    void save() {
        //given
        MusicalNotice musicalNotice = MusicalNotice.builder()
                .id("1234")
                .title("오페라의 유령")
                .openDateTime(LocalDateTime.now())
                .url("http://example.com")
                .siteCategory(SiteCategory.INTERPARK)
                .build();
        when(musicalNoticeRepository.save(musicalNotice)).thenReturn(musicalNotice);

        //when
        MusicalNotice save = musicalNoticeService.save(musicalNotice);

        //then
        assertThat(save.getId()).isEqualTo(musicalNotice.getId());
        assertThat(save.getTitle()).isEqualTo(musicalNotice.getTitle());
        assertThat(save.getOpenDateTime()).isEqualTo(musicalNotice.getOpenDateTime());
        assertThat(save.getUrl()).isEqualTo(musicalNotice.getUrl());
        assertThat(save.getSiteCategory()).isEqualTo(musicalNotice.getSiteCategory());
    }
    @Nested
    @DisplayName("조회")
    class FindTest {
        @Test
        @DisplayName("아이디 조회")
        void findById() {
            //given
            LocalDateTime now = LocalDateTime.now();
            MusicalNotice musicalNotice = MusicalNotice.builder()
                    .id("1234")
                    .title("오페라의 유령")
                    .openDateTime(now)
                    .url("http://example.com")
                    .siteCategory(SiteCategory.INTERPARK)
                    .build();
            when(musicalNoticeRepository.save(musicalNotice)).thenReturn(musicalNotice);
            when(musicalNoticeRepository.findById("1234")).thenReturn(Optional.of(musicalNotice));

            //when
            musicalNoticeService.save(musicalNotice);
            MusicalNotice musicalNotice1 = musicalNoticeService.findById("1234");

            //then
            assertThat(musicalNotice1.getId()).isEqualTo("1234");
            assertThat(musicalNotice1.getTitle()).isEqualTo("오페라의 유령");
            assertThat(musicalNotice1.getUrl()).isEqualTo("http://example.com");
            assertThat(musicalNotice1.getOpenDateTime()).isEqualTo(now);
            assertThat(musicalNotice1.getSiteCategory()).isEqualTo(SiteCategory.INTERPARK);
        }

        @Test
        @DisplayName("아이디 조회 실패")
        void findByIdFail() {
            //given
            when(musicalNoticeRepository.findById(any(String.class))).thenReturn(Optional.empty());

            //when, then
            assertThrows(
                    CustomNotFoundException.class, ()->{
                        musicalNoticeService.findById("1234");
                    }
            );
        }
        @Test
        @DisplayName("마감 임박 조회")
        void findDeadline() {

            //given
            LocalDateTime now = LocalDateTime.now(); // 검색에 사용할 LocalDateTime
            PageRequest pageRequest = PageRequest.of(0, 2); // 검색에 사용할 Pageable
            LocalDateTime now2 = LocalDateTime.now(); // 저장에 사용할 LocalDateTime
            MusicalNotice musicalNotice1 = MusicalNotice.builder()
                    .id("1234")
                    .title("오페라의 유령")
                    .openDateTime(now2)
                    .url("http://example.com")
                    .siteCategory(SiteCategory.INTERPARK)
                    .build();
            MusicalNotice musicalNotice2 = MusicalNotice.builder()
                    .id("12345")
                    .title("오페라의 유령2")
                    .openDateTime(now2)
                    .url("http://example.com")
                    .siteCategory(SiteCategory.INTERPARK)
                    .build();
            ArrayList<MusicalNotice> list = new ArrayList<>();
            list.add(musicalNotice1);
            list.add(musicalNotice2);
            PageImpl page = new PageImpl(list);
            when(musicalNoticeRepository.findByOpenDateTimeAfterOrderByOpenDateTime(any(LocalDateTime.class), any(Pageable.class)))
                    .thenReturn(page);

            //when
            List<MusicalNotice> musicalNotices = musicalNoticeService.findDeadlineNotice(pageRequest);

            //then
            assertThat(musicalNotices.size()).isEqualTo(2);
        }
        @Test
        @DisplayName("최근 등록 조회")
        void findLatest() {
            //given
            LocalDateTime now = LocalDateTime.now(); // 검색에 사용할 LocalDateTime
            PageRequest pageRequest = PageRequest.of(0, 2); // 검색에 사용할 Pageable
            LocalDateTime now2 = LocalDateTime.now(); // 저장에 사용할 LocalDateTime
            MusicalNotice musicalNotice1 = MusicalNotice.builder()
                    .id("1234")
                    .title("오페라의 유령")
                    .openDateTime(now2)
                    .url("http://example.com")
                    .siteCategory(SiteCategory.INTERPARK)
                    .build();
            MusicalNotice musicalNotice2 = MusicalNotice.builder()
                    .id("12345")
                    .title("오페라의 유령2")
                    .openDateTime(now2)
                    .url("http://example.com")
                    .siteCategory(SiteCategory.INTERPARK)
                    .build();
            ArrayList<MusicalNotice> list = new ArrayList<>();
            list.add(musicalNotice1);
            list.add(musicalNotice2);
            PageImpl page = new PageImpl(list);
            when(musicalNoticeRepository.findAllByOrderByCreatedTimeDesc(any(Pageable.class)))
                    .thenReturn(page);

            //when
            List<MusicalNotice> musicalNotices = musicalNoticeService.findLatestNotice(pageRequest);

            //then
            assertThat(musicalNotices.size()).isEqualTo(2);
        }
        @Test
        @DisplayName("제목 조회")
        void findSearch() {
            //given
            LocalDateTime now = LocalDateTime.now(); // 검색에 사용할 LocalDateTime
            PageRequest pageRequest = PageRequest.of(0, 2); // 검색에 사용할 Pageable
            LocalDateTime now2 = LocalDateTime.now(); // 저장에 사용할 LocalDateTime
            MusicalNotice musicalNotice1 = MusicalNotice.builder()
                    .id("1234")
                    .title("오페라의 유령")
                    .openDateTime(now2)
                    .url("http://example.com")
                    .siteCategory(SiteCategory.INTERPARK)
                    .build();
            MusicalNotice musicalNotice2 = MusicalNotice.builder()
                    .id("12345")
                    .title("오페라의 유령2")
                    .openDateTime(now2)
                    .url("http://example.com")
                    .siteCategory(SiteCategory.INTERPARK)
                    .build();
            ArrayList<MusicalNotice> list = new ArrayList<>();
            list.add(musicalNotice1);
            list.add(musicalNotice2);
            PageImpl page = new PageImpl(list);
            when(musicalNoticeRepository.findBySearch(any(String.class), any(Pageable.class)))
                    .thenReturn(page);

            //when
            List<MusicalNotice> musicalNotices = musicalNoticeService.findBySearch("오페라", pageRequest);

            //then
            assertThat(musicalNotices.size()).isEqualTo(2);
        }
    }
}