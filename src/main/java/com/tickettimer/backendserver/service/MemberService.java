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
    private final MemberRepository memberRepository;

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public Member findById(Long id) {
        Optional<Member> findMember = memberRepository.findById(id);
        return findMember.orElseThrow(
                () -> new CustomNotFoundException("id", id.toString())
        );
    }

    /**
     *
     * @param serverId resource server로부터 받은 serverId
     * @return Member
     * 해당 serverId가 없다면 NotFoundException 발생
     */
    public Member findByServerId(String serverId) {
        Optional<Member> findMember = memberRepository.findByServerId(serverId);
        return findMember.orElseThrow(
                () -> new CustomNotFoundException("id", serverId)
        );
    }
}
