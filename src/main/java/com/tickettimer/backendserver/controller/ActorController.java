package com.tickettimer.backendserver.controller;

import com.tickettimer.backendserver.domain.musical.Actor;
import com.tickettimer.backendserver.domain.musical.Musical;
import com.tickettimer.backendserver.dto.ActorRequestDto;
import com.tickettimer.backendserver.dto.ResultResponse;
import com.tickettimer.backendserver.service.ActorService;
import com.tickettimer.backendserver.service.MusicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/actors")
public class ActorController {
    private final ActorService actorService;
    private final MusicalService musicalService;

    @PostMapping
    public ResponseEntity<ResultResponse> postMusical(@RequestBody ActorRequestDto actorRequestDto) {
        // Actor 엔티티 생성
        String musicalId = actorRequestDto.getMusicalId();
        Musical findMusical = musicalService.findById(musicalId);
        Actor actor = Actor.builder()
                .actorName(actorRequestDto.getActorName())
                .roleName(actorRequestDto.getRoleName())
                .profileUrl(actorRequestDto.getProfileUrl())
                .musical(findMusical).build();

        // Actor 엔티티 저장
        Actor saveActor = actorService.save(actor);

        // 결과 반환
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message(saveActor.getMusical().getId() + " : 뮤지컬 정보를 저장했습니다.")
                .result(saveActor).build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }
}
