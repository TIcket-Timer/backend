package com.tickettimer.backendserver.service;

import com.tickettimer.backendserver.domain.Member;
import com.tickettimer.backendserver.domain.MemberMusical;
import com.tickettimer.backendserver.domain.Memo;
import com.tickettimer.backendserver.exception.CustomNotFoundException;
import com.tickettimer.backendserver.repository.MemberMusicalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberMusicalService {
    private final MemberMusicalRepository memberMusicalRepository;

    // 저장 (알람 설정)
    public MemberMusical save(MemberMusical memberMusical) {
        return  memberMusicalRepository.save(memberMusical);
    }

    // 조회 findById
    public MemberMusical findById(Long id) {
        Optional<MemberMusical> findMemberMusical = memberMusicalRepository.findById(id);
        return findMemberMusical.orElseThrow(
                () -> new CustomNotFoundException("alarmId", id.toString())
        );
    }

    // 조회 findByMember
    public List<MemberMusical> findByMember(Member member) {
        return memberMusicalRepository.findByMember(member);
    }

    // 삭제 (알람 설정 취소)
    public void delete(Long id) {
        memberMusicalRepository.deleteById(id);
    }

}
