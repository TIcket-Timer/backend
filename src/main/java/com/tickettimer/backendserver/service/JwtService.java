package com.tickettimer.backendserver.service;

import com.tickettimer.backendserver.dto.TokenInfo;
import com.tickettimer.backendserver.dto.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
//    private final TokenRepository tokenRepository;
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
//        Token token1 = new Token(id, token);
//        tokenRepository.save(token1);
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