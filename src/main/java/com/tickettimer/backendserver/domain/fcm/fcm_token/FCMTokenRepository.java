package com.tickettimer.backendserver.domain.fcm.fcm_token;

import com.tickettimer.backendserver.domain.member.FCMToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FCMTokenRepository extends JpaRepository<FCMToken, Long> {
}
