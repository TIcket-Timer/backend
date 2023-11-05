package com.tickettimer.backendserver.domain.musical.ranking;

import com.tickettimer.backendserver.global.exception.CustomNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
