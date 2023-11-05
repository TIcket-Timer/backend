package com.tickettimer.backendserver.domain.alarm;

import com.tickettimer.backendserver.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    List<Alarm> findByMember(Member member);
}
