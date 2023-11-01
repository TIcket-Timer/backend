package com.tickettimer.backendserver.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tickettimer.backendserver.auth.PrincipalDetails;
import com.tickettimer.backendserver.domain.FCMToken;
import com.tickettimer.backendserver.domain.Member;
import com.tickettimer.backendserver.domain.Token;
import com.tickettimer.backendserver.dto.*;
import com.tickettimer.backendserver.exception.CustomNotFoundException;
import com.tickettimer.backendserver.repository.TokenRepository;
import com.tickettimer.backendserver.service.*;
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
import java.util.UUID;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final MemberService memberService;
    private final FCMTokenService fcmTokenService;
    private final ObjectMapper objectMapper;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenRepository tokenRepository;
    private final Long tokenExpiredMs;
    private final AppleService appleService;
    private final KakaoOpenFeign kakaoOpenFeign;

    private final String password; // 회원 가입시 넣어줄 비밀번호. application.properties에서 관리
    public JwtAuthenticationFilter(
            AuthenticationManager authenticationManager,
            MemberService memberService,
            FCMTokenService fcmTokenService,
            ObjectMapper objectMapper,
            JwtService jwtService,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            String password,
            TokenRepository tokenRepository,
            Long tokenExpiredMs,
            AppleService appleService,
            KakaoOpenFeign kakaoOpenFeign
    ) {
        this.authenticationManager = authenticationManager;
        this.memberService = memberService;
        this.fcmTokenService = fcmTokenService;
        this.objectMapper = objectMapper;
        this.jwtService = jwtService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.password = password;
        this.tokenExpiredMs = tokenExpiredMs;
        this.tokenRepository = tokenRepository;
        this.appleService = appleService;
        this.kakaoOpenFeign = kakaoOpenFeign;
    }


    //로그인을 시도할 때 실행
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        String resource = request.getHeader("resource"); // resource server 이름 ex) 카카오, 애플
        String accessToken = request.getHeader("Authorization");
        String fcmToken = request.getHeader("fcmToken");
        log.info("소셜 로그인 시도 resource : {}", resource);

        // 카카오 로그인이라면
        if (resource.equals(ResourceType.KAKAO.getName())) {
            KakaoResponse myInfo = kakaoOpenFeign.getMyInfo(accessToken);
            KakaoLogoutResponse logout = kakaoOpenFeign.logout(accessToken);

            // 혹시라도 액세스 토큰의 유출에 대비하기 위해서 유저 정보 가져오고 나면 바로 토큰 만료
            log.info("카카오 액세스 토큰 만료 완료: {}", logout.getId());

            // 서버 식별 아이디
            // 혹시 플랫폼별 중복 가능성 때문에 이름 붙임
            String serverId = "kakao" + myInfo.getId().toString();
            // 닉네임
            // 닉네임은 중복 가능
            // UUID는 식별하기보다는 단순히 랜덤 문자열을 생성하기 위해서 사용
            String nickname = UUID.randomUUID().toString().substring(0, 9);

            // 이메일
            String email = myInfo.getKakao_account().getEmail();

            try{
                // 해당 serverId 데이터베이스 존재 여부. 존재하지 않으면 CustomNotFoundException 반환
                Member member = memberService.findByServerId(serverId);
                fcmTokenService.update(member.getFcmToken(), fcmToken);
            } catch(CustomNotFoundException e) {
                // 해당 유저가 데이터베이스에 없으면 회원가입 처리
                // 비밀번호는 서버에서 만든 값
                Member newMember = Member.builder()
                        .serverId(serverId)
                        .nickname(nickname)
                        .password(bCryptPasswordEncoder.encode(password))
                        .email(email).build();
                memberService.save(newMember);
                FCMToken newFcmToken = FCMToken.builder()
                                .member(newMember)
                                        .token(fcmToken)
                                                .build();
                FCMToken save = fcmTokenService.save(newFcmToken);
                System.out.println("update time = " + save.getModifiedDate());
                System.out.println("update time = " + save.getCreatedTime());

            }
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(serverId, password);
            Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            return authenticate;

        } else if (resource.equals(ResourceType.APPLE.getName())){
            // 애플 로그인
            Map<String, String> userInfo = appleService.getUserInfo(accessToken);
            String serverId = "apple" + userInfo.get("sub");
            String nickname = UUID.randomUUID().toString().substring(0, 9);
            String email = userInfo.get("email");
            try{
                // 해당 serverId 데이터베이스 존재 여부. 존재하지 않으면 CustomNotFoundException 반환
                Member member = memberService.findByServerId(serverId);
                fcmTokenService.update(member.getFcmToken(), fcmToken);
            } catch(CustomNotFoundException e) {
                // 해당 유저가 데이터베이스에 없으면 회원가입 처리
                // 비밀번호는 서버에서 만든 값
                Member newMember = Member.builder()
                        .serverId(serverId)
                        .nickname(nickname)
                        .password(bCryptPasswordEncoder.encode(password))
                        .email(email).build();
                memberService.save(newMember);
                FCMToken newFcmToken = FCMToken.builder()
                        .member(newMember)
                        .token(fcmToken)
                        .build();
                FCMToken save = fcmTokenService.save(newFcmToken);
                System.out.println("update time = " + save.getModifiedDate());
                System.out.println("update time = " + save.getCreatedTime());

            }
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(serverId, password);
            Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            return authenticate;
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
        Token token = new Token(member.getId(), refreshToken, tokenExpiredMs);
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