package com.tickettimer.backendserver.repository;

import com.tickettimer.backendserver.domain.MemberMusical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberMusicalRepository extends JpaRepository<MemberMusical, Long> {
}
