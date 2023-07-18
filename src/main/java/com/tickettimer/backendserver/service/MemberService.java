package com.tickettimer.backendserver.service;

import com.tickettimer.backendserver.domain.Member;
import com.tickettimer.backendserver.exception.CustomNotFoundException;
import com.tickettimer.backendserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
//    private final MemberRepository memberRepository;
//
//    public Member save(Member member) {
//        Optional<Member> save = memberRepository.save(member);
//        return save.orElseThrow(
//                () -> new CustomNotFoundException("memberId", member.getServerId())
//        );
//    }
//
//    public Member findById(Long id) {
//        Optional<Member> findMember = memberRepository.findById(id);
//        findMember.ifPresent(
//                value->{
//
//                }
//        );
//    }
}
