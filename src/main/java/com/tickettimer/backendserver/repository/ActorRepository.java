package com.tickettimer.backendserver.repository;

import com.tickettimer.backendserver.domain.musical.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
    List<Actor> findByActorName(String actorName);
}
