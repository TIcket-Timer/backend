package com.tickettimer.backendserver.service;

import com.tickettimer.backendserver.domain.musical.Actor;
import com.tickettimer.backendserver.repository.ActorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActorService {
    private final ActorRepository actorRepository;

    public Actor save(Actor actor) {
        return actorRepository.save(actor);
    }

    public List<Actor> findByActorName(String actorName) {
        return actorRepository.findByActorName(actorName);
    }
}
