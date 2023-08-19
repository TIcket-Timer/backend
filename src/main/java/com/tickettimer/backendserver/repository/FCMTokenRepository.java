package com.tickettimer.backendserver.repository;

import com.tickettimer.backendserver.domain.FCMToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FCMTokenRepository extends JpaRepository<FCMToken, Long> {
    List<FCMToken> findByIsActivated(boolean isActivated);
}
