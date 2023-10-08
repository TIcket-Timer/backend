package com.tickettimer.backendserver.service;

import com.tickettimer.backendserver.domain.FCMToken;
import com.tickettimer.backendserver.domain.Member;
import com.tickettimer.backendserver.domain.Token;
import com.tickettimer.backendserver.dto.TokenInfo;
import com.tickettimer.backendserver.dto.TokenType;
import com.tickettimer.backendserver.exception.CustomNotFoundException;
import com.tickettimer.backendserver.repository.FCMTokenRepository;
import com.tickettimer.backendserver.repository.MemberRepository;
import com.tickettimer.backendserver.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final TokenRepository tokenRepository;
    private final MemberRepository memberRepository;
    private final FCMTokenRepository fcmTokenRepository;
    @Value("${jwt.secretKey}") //application.properties에 저장되어 있는 값을 가져온다.
    private String secretKey;
    @Value("${jwt.access.expiredMs}") //application.properties에 저장되어 있는 값을 가져온다.
    private Long accessExpiredMs;
    @Value("${jwt.refresh.expiredMs}") //application.properties에 저장되어 있는 값을 가져온다.
    private Long refreshExpiredMs;

    public String getServerId(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("serverId", String.class);
    }
    public Long getId(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("id", Long.class);
    }

    public String getTokenType(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("type", String.class);
    }

    public boolean isExpired(String token) {
        return !Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }

    public String createAccessToken(String memberServerId, Long id) {
        Claims claims = Jwts.claims();
        claims.put("serverId", memberServerId);
        claims.put("id", id);
        claims.put("type", TokenType.ACCESS.getName());

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiredMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return token;
    }

    /**
     *
     * @param serverId: oauth2를 통해 만들어진 아이디
     * @param id: PK, Long 타입 아이디
     * @return refresh token 반환
     * redis에 refresh token을 저장한다.
     */
    public String createRefreshToken(String serverId, Long id) {
        Claims claims = Jwts.claims();
        claims.put("id", id);
        claims.put("serverId", serverId);
        claims.put("type", TokenType.REFRESH.getName());

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiredMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        Token token1 = new Token(id, token, refreshExpiredMs);
        tokenRepository.save(token1);

        Optional<Member> findMember = memberRepository.findByServerId(serverId);
        if (findMember.isEmpty()) {
            throw new CustomNotFoundException("server id", serverId);
        }
        else{ // 회원이 존재하면 FCM token 수정 시간 업데이트
            FCMToken fcmToken = findMember.get().getFcmToken();
            fcmTokenRepository.save(fcmToken);
        }
        return token;
    }

//    public boolean validateRefreshToken(String refreshToken) {
//        boolean expired = isExpired(refreshToken);
//        System.out.println("expired = " + expired);
//        Long pk = getId(refreshToken);
////        String storedRefreshToken = tokenRepository.findById(pk).get().toString();
//        return expired && refreshToken.equals(storedRefreshToken);
//
//    }
}