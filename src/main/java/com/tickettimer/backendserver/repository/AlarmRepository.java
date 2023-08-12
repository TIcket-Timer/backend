package com.tickettimer.backendserver.repository;

import com.tickettimer.backendserver.domain.Member;
import com.tickettimer.backendserver.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    List<Alarm> findByMember(Member member);
}
