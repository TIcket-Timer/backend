package com.tickettimer.backendserver.repository;

import com.tickettimer.backendserver.domain.musical.MusicalNotice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface MusicalNoticeRepository extends JpaRepository<MusicalNotice, String> {
    Page<MusicalNotice> findAllByOrderByCreatedTimeDesc(Pageable pageable);
    Page<MusicalNotice> findByOpenDateTimeAfterOrderByOpenDateTime(LocalDateTime localDateTime, Pageable pageable);

}
