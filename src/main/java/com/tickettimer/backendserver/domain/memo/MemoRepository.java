package com.tickettimer.backendserver.domain.memo;

import com.tickettimer.backendserver.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long> {
    List<Memo> findByMember(Member member);
    Optional<Memo> findByDate(LocalDate date);
}
