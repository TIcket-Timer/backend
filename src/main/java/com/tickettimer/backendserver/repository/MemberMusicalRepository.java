package com.tickettimer.backendserver.repository;

import com.tickettimer.backendserver.domain.Member;
import com.tickettimer.backendserver.domain.MemberMusical;
import com.tickettimer.backendserver.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberMusicalRepository extends JpaRepository<MemberMusical, Long> {

    List<MemberMusical> findByMember(Member member);
}
