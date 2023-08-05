package com.tickettimer.backendserver.service;

import com.tickettimer.backendserver.domain.Member;
import com.tickettimer.backendserver.domain.Memo;
import com.tickettimer.backendserver.domain.musical.Musical;
import com.tickettimer.backendserver.exception.CustomNotFoundException;
import com.tickettimer.backendserver.repository.MusicalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MusicalService {
    private final MusicalRepository musicalRepository;

    // 저장
    public Musical save(Musical musical) {
        return  musicalRepository.save(musical);
    }

    // 조회 findById
    public Musical findById(Long id) {
        Optional<Musical> findMusical = musicalRepository.findById(id);
        return findMusical.orElseThrow(
                () -> new CustomNotFoundException("musicalId", id.toString())
        );
    }

    // 수정
    public Musical update(Long id, Musical newMusical) {
        Musical musical = musicalRepository.findById(id).get();
        musical = newMusical;
        return musicalRepository.save(musical);
    }

    // 삭제
    public void delete(Long id) {
        musicalRepository.deleteById(id);
    }
}
