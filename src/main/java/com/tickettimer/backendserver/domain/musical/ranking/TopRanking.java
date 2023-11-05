package com.tickettimer.backendserver.domain.musical.ranking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@RedisHash(value = "ranking")
@AllArgsConstructor
@Getter
public class TopRanking {
    @Id
    private String siteCategory;
    private List<String> musicalIds; // 뮤지컬 top ranking pk들 저장
}
