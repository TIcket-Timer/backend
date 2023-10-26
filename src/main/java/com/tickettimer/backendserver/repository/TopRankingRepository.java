package com.tickettimer.backendserver.repository;

import com.tickettimer.backendserver.domain.TopRanking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface TopRankingRepository extends CrudRepository<TopRanking, String> {
}
