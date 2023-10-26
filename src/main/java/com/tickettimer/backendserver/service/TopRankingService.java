package com.tickettimer.backendserver.service;

import com.tickettimer.backendserver.domain.TopRanking;
import com.tickettimer.backendserver.exception.CustomNotFoundException;
import com.tickettimer.backendserver.repository.MusicalRepository;
import com.tickettimer.backendserver.repository.TopRankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TopRankingService {
    private final TopRankingRepository topRankingRepository;

    public TopRanking findById(String name) {
        Optional<TopRanking> topRanking = topRankingRepository.findById(name);
        return topRanking.orElseThrow(
                () -> new CustomNotFoundException("id", name)
        );

    }

    public TopRanking save(TopRanking topRanking) {
        return topRankingRepository.save(topRanking);
    }
}
