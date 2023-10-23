package com.tickettimer.backendserver.service;

import com.tickettimer.backendserver.domain.Member;
import com.tickettimer.backendserver.domain.musical.SiteCategory;
import com.tickettimer.backendserver.exception.CustomNotFoundException;
import com.tickettimer.backendserver.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public Member findById(Long id) {
        Optional<Member> findMember = memberRepository.findById(id);
        return findMember.orElseThrow(
                () -> new CustomNotFoundException("memberId", id.toString())
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

    // fcm 알람 받기 등록
    public void updateFcmAlarm(Long id , SiteCategory siteCategory, boolean bool) {

        Optional<Member> findMember = memberRepository.findById(id);
        if (findMember.isEmpty()) {
            throw new CustomNotFoundException("id", id.toString());
        }
        Member member = findMember.get();
        switch (siteCategory) {
            case INTERPARK -> member.setInterAlarm(bool);
            case YES24 -> member.setYesAlarm(bool);
            case MELON -> member.setMelonAlarm(bool);
        }
        memberRepository.save(member);
    }

    public void updateNickname(Long id, String name) {
        Optional<Member> findMember = memberRepository.findById(id);
        if (findMember.isEmpty()) {
            throw new CustomNotFoundException("id", id.toString());
        }
        Member member = findMember.get();
        member.setNickname(name);
        memberRepository.save(member);
    }

    public List<Member> findMemberBySiteAlarm(SiteCategory siteCategory) {
        return switch (siteCategory) {
            case INTERPARK -> memberRepository.findByInterAlarm(true);
            case YES24 -> memberRepository.findByYesAlarm(true);
            case MELON -> memberRepository.findByMelonAlarm(true);
        };
    }
}
