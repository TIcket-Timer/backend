package com.tickettimer.backendserver.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tickettimer.backendserver.auth.PrincipalDetails;
import com.tickettimer.backendserver.domain.Member;
import com.tickettimer.backendserver.domain.Token;
import com.tickettimer.backendserver.dto.ResourceType;
import com.tickettimer.backendserver.dto.ResultResponse;
import com.tickettimer.backendserver.dto.TokenInfo;
import com.tickettimer.backendserver.exception.CustomNotFoundException;
import com.tickettimer.backendserver.repository.TokenRepository;
import com.tickettimer.backendserver.service.JwtService;
import com.tickettimer.backendserver.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final MemberService memberService;
    private final ObjectMapper objectMapper;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenRepository tokenRepository;

    private final String password; // 회원 가입시 넣어줄 비밀번호. application.properties에서 관리
    public JwtAuthenticationFilter(
            AuthenticationManager authenticationManager,
            MemberService memberService,
            ObjectMapper objectMapper,
            JwtService jwtService,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            String password,
            TokenRepository tokenRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.memberService = memberService;
        this.objectMapper = objectMapper;
        this.jwtService = jwtService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.password = password;
        this.tokenRepository = tokenRepository;
    }


    //로그인을 시도할 때 실행
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        String resource = request.getHeader("resource"); // resource server 이름 ex) 카카오, 애플
        String accessToken = request.getHeader("Authorization");

        // 카카오 로그인이라면
        if (resource.equals(ResourceType.KAKAO.getName())) {
            // header 생성
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(HttpHeaders.AUTHORIZATION, accessToken);
            httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");

            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(httpHeaders);

            // 사용자 정보 요청하기
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Object> memberInfo = restTemplate.exchange(
                    "https://kapi.kakao.com/v2/user/me",
                    HttpMethod.GET,
                    httpEntity,
                    Object.class
            );
            Map map = objectMapper.convertValue(memberInfo.getBody(), Map.class);
            Map properties = objectMapper.convertValue(map.get("properties"), Map.class);
            Map kakaoAccount = objectMapper.convertValue(map.get("kakao_account"), Map.class);

            // 서버 식별 아이디
            String serverId = "kakao" + map.get("id");

            // 닉네임
            String nickname = (String) properties.get("nickname");

            // 이메일
            String email = (String) kakaoAccount.get("email");

            try{
                // 해당 serverId 데이터베이스 존재 여부. 존재하지 않으면 CustomNotFoundException 반환
                memberService.findByServerId(serverId);
            } catch(CustomNotFoundException e) {
                // 해당 유저가 데이터베이스에 없으면 회원가입 처리
                // 비밀번호는 서버에서 만든 값
                Member newMember = Member.builder()
                        .serverId(serverId)
                        .nickname(nickname)
                        .password(bCryptPasswordEncoder.encode(password))
                        .email(email).build();
                memberService.save(newMember);

            }
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(serverId, password);
            Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            return authenticate;

        } else if (resource == ResourceType.APPLE.getName()){
            // 애플 로그인
            return null;
        } else{
            return null;
        }
    }
    //인증을 성공하면 실행
    //response Authorization header에 jwt를 담아서 보내줌
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult)
    {
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        Member member = principalDetails.getMember();
        String serverAccessToken = jwtService.createAccessToken(
                member.getServerId(),
                member.getId()
        );

        String refreshToken = jwtService.createRefreshToken(member.getServerId(), member.getId());
        TokenInfo tokenInfo = TokenInfo.builder()
                .accessToken(serverAccessToken)
                .refreshToken(refreshToken).build();
        //TokeknRepository에 refresh token을 저장한다.
        Token token = new Token(member.getId(), refreshToken);
        tokenRepository.save(token);
        //서블릿으로 JSON 응답
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        ResultResponse res = ResultResponse.builder()
                .code(HttpServletResponse.SC_OK)
                .message("로그인 성공")
                .result(tokenInfo).build();

        log.info("{} : 로그인 성공", member.getServerId());
        try {
            String s = objectMapper.writeValueAsString(res);
            PrintWriter writer = response.getWriter();
            writer.write(s);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
//    @Override
//    protected void unsuccessfulAuthentication(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            AuthenticationException failed
//    ) {
//        //비밀번호 틀림
//        writeErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, failed.getMessage());
//    }
    private void writeErrorResponse(HttpServletResponse response, int status, String message) {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        ResultResponse res = ResultResponse.builder()
                .code(status)
                .message(message).build();
        try {
            String s = objectMapper.writeValueAsString(res);
            PrintWriter writer = response.getWriter();
            writer.write(s);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}