package com.tickettimer.backendserver.repository;

import com.tickettimer.backendserver.domain.musical.Musical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicalRepository extends JpaRepository<Musical, Long> {
}
