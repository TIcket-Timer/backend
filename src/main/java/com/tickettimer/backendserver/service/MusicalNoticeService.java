package com.tickettimer.backendserver.service;

import com.tickettimer.backendserver.domain.musical.MusicalNotice;
import com.tickettimer.backendserver.exception.CustomNotFoundException;
import com.tickettimer.backendserver.repository.MusicalNoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MusicalNoticeService {
    private final MusicalNoticeRepository musicalNoticeRepository;
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
    public void delete(String id) {
        musicalNoticeRepository.deleteById(id);
    }
}
