package com.tickettimer.backendserver.repository;

import com.tickettimer.backendserver.domain.Member;
import com.tickettimer.backendserver.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long> {
    List<Memo> findByMember(Member member);
}
