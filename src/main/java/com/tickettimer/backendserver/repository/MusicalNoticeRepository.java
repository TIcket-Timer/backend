package com.tickettimer.backendserver.repository;

import com.tickettimer.backendserver.domain.musical.MusicalNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicalNoticeRepository extends JpaRepository<MusicalNotice, String> {

}
