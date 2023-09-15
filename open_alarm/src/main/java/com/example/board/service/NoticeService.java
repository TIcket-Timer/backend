package com.example.board.service;

import com.example.board.domain.Notice;
import com.example.board.exception.CustomNotFoundException;
import com.example.board.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    /**
     * 예매오픈공지 저장
     */
    public void saveNotice(Notice notice) {
        noticeRepository.save(notice);
    }

    /**
     * 예매오픈공지 전체 불러오기
     */
    public List<Notice> readNotice() {
        return noticeRepository.findAll();
    }
    /**
     * 예매오픈공지 상세 불러오기
     */
    public Notice readNoticeMusicalId(Long musicalId) {
        Optional<Notice> notice = noticeRepository.findById(musicalId);
        return notice.orElseThrow(
                () -> new CustomNotFoundException("noticeId", musicalId.toString()));
    }

    /**
     * 예매오픈공지 삭제
     */
    public void deleteNotice(Long musicalId) {
        noticeRepository.deleteById(musicalId);
    }

}
