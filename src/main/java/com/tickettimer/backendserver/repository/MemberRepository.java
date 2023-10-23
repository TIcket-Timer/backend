package com.tickettimer.backendserver.repository;

import com.tickettimer.backendserver.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member save(Member member);
    Optional<Member> findByServerId(String serverId);

    List<Member> findByInterAlarm(boolean interAlarm);
    List<Member> findByMelonAlarm(boolean melonAlarm);
    List<Member> findByYesAlarm(boolean yesAlarm);

}
