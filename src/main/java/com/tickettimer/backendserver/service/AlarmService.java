package com.tickettimer.backendserver.service;

import com.tickettimer.backendserver.domain.Member;
import com.tickettimer.backendserver.domain.Alarm;
import com.tickettimer.backendserver.exception.CustomNotFoundException;
import com.tickettimer.backendserver.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmRepository alarmRepository;

    // 저장 (알람 설정)
    public Alarm save(Alarm alarm) {
        return  alarmRepository.save(alarm);
    }

    // 조회 findById
    public Alarm findById(Long id) {
        Optional<Alarm> findMemberMusical = alarmRepository.findById(id);
        return findMemberMusical.orElseThrow(
                () -> new CustomNotFoundException("alarmId", id.toString())
        );
    }

    // 조회 findByMember
    public List<Alarm> findByMember(Member member) {
        return alarmRepository.findByMember(member);
    }

    // 삭제 (알람 설정 취소)
    public void delete(Long id) {
        alarmRepository.deleteById(id);
    }


}
