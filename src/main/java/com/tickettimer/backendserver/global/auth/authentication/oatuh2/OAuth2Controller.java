package com.tickettimer.backendserver.global.auth.authentication.oatuh2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tickettimer.backendserver.domain.member.Member;
import com.tickettimer.backendserver.global.auth.authorization.TokenInfo;
import com.tickettimer.backendserver.domain.member.MemberRepository;
import com.tickettimer.backendserver.global.auth.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

//@RestController
@RequestMapping("/api/oauth2")
@RequiredArgsConstructor
public class OAuth2Controller {
    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    @GetMapping
    public String hello() {
        return "hello";
    }
    @GetMapping("/kakao")
    public ResponseEntity<TokenInfo> getAccessAndRefresh(@RequestHeader("access-token") String kakaoAccessToken) {
        // header 생성
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, kakaoAccessToken);
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(httpHeaders);

        // 사용자 정보 요청하기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                httpEntity,
                Object.class
        );
        Map map = objectMapper.convertValue(response.getBody(), Map.class);
        System.out.println("response = " + response.getBody());
        System.out.println("map = " + map);
        Map properties = objectMapper.convertValue(map.get("properties"), Map.class);
        Map kakaoAccount = objectMapper.convertValue(map.get("kakao_account"), Map.class);

        // 서버 식별 아이디
        String serverId = "kakao" + map.get("id");

        // 닉네임
        String nickname = (String) properties.get("nickname");

        // 이메일
        String email = (String) kakaoAccount.get("email");
        System.out.println(serverId + nickname + email);
        Optional<Member> findMember = memberRepository.findByServerId(serverId);

        Member member = null;
        // 유저가 데이터베이스에 없으면 회원가입 처리
        if (findMember.isEmpty()) {
            Member newMember = Member.builder()
                    .serverId(serverId)
                    .nickname(nickname)
                    .email(email).build();
            memberRepository.save(newMember);
        } else {
            member = findMember.get();
        }
        String accessToken = jwtService.createAccessToken(
                member.getServerId(),
                member.getId()
        );

        String refreshToken = jwtService.createRefreshToken(member.getServerId(), member.getId());
        TokenInfo tokenInfo = TokenInfo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken).build();
        return new ResponseEntity<>(tokenInfo, HttpStatus.OK);


    }

}