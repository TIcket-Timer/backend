package com.tickettimer.backendserver.domain.musical.notice;

import com.tickettimer.backendserver.domain.fcm.FCMService;
import com.tickettimer.backendserver.global.exception.CustomNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MusicalNoticeService {
    private final MusicalNoticeRepository musicalNoticeRepository;
    private final FCMService fcmService;
    // 저장
    public MusicalNotice save(MusicalNotice musicalNotice) {
        return  musicalNoticeRepository.save(musicalNotice);
    }

    // 조회 findById
    public MusicalNotice findById(String id) {
        Optional<MusicalNotice> findMusical = musicalNoticeRepository.findById(id);
        return findMusical.orElseThrow(
                () -> new CustomNotFoundException("musicalNoticeId", id)
        );
    }

    // 마감 임박 조회
    public List<MusicalNotice> findDeadlineNotice(Pageable pageable) {
        Page<MusicalNotice> musicalNoticePage = musicalNoticeRepository.findByOpenDateTimeAfterOrderByOpenDateTime(LocalDateTime.now(), pageable);
        List<MusicalNotice> musicalNotices = musicalNoticePage.getContent();
        return musicalNotices;
    }

    // 최근 등록 조회
    public List<MusicalNotice> findLatestNotice(Pageable pageable) {
        Page<MusicalNotice> musicalNoticePage = musicalNoticeRepository.findAllByOrderByCreatedTimeDesc(pageable);
        List<MusicalNotice> musicalNotices = musicalNoticePage.getContent();
        return musicalNotices;
    }

    // 제목으로 검색
    public List<MusicalNotice> findBySearch(String name, Pageable pageable) {
        Page<MusicalNotice> musicalNoticePage = musicalNoticeRepository.findBySearch(name, pageable);
        List<MusicalNotice> musicalNotices = musicalNoticePage.getContent();
        return musicalNotices;
    }

//
//    public void delete(String id) {
//        musicalNoticeRepository.deleteById(id);
//    }
}
