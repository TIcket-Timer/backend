package com.tickettimer.backendserver.service;

import com.tickettimer.backendserver.auth.PrincipalDetails;
import com.tickettimer.backendserver.domain.Member;
import com.tickettimer.backendserver.exception.CustomNotFoundException;
import com.tickettimer.backendserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService{
    private final MemberRepository memberRepository;

    /**
     *
     * @param serverId resource server 정보 조합한 아이디. 식별 목적.
     * @return Member 엔티티를 담은 PrincipalDetails
     * 만약 해당 serverId가 데이터베이스에 없으면 CustomNotFoundException 발생.
     */
    @Override
    public UserDetails loadUserByUsername(String serverId) {
        Optional<Member> member = memberRepository.findByServerId(serverId);
        member.orElseThrow(
                () -> new CustomNotFoundException("member serverId", serverId)
        );

        return new PrincipalDetails(member.get());
    }
}