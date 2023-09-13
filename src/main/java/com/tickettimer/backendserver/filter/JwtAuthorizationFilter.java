package com.tickettimer.backendserver.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tickettimer.backendserver.auth.PrincipalDetails;
import com.tickettimer.backendserver.domain.Token;
import com.tickettimer.backendserver.dto.AuthorizationRequest;
import com.tickettimer.backendserver.dto.ResultResponse;
import com.tickettimer.backendserver.dto.TokenInfo;
import com.tickettimer.backendserver.dto.TokenType;
import com.tickettimer.backendserver.exception.InvalidTokenException;
import com.tickettimer.backendserver.exception.RefreshTokenNotSameException;
import com.tickettimer.backendserver.repository.TokenRepository;
import com.tickettimer.backendserver.service.JwtService;
import com.tickettimer.backendserver.service.PrincipalDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

/**
 * 반환 에러 종류
 * CustomNotFoundException : 멤버를 찾지 못 함
 * InvalidTokenException : jwt의 형태가 아님
 * ExpiredJwtException : jwt의 유효 기간이 만료 됨
 * RefreshTokenNotSameException : 데이터베이스에 저장된 refresh token 값과 일치하지 않음
 */
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final JwtService jwtService;
    private final PrincipalDetailsService principalDetailsService;
    private final ObjectMapper objectMapper;
    private final TokenRepository tokenRepository;

    public JwtAuthorizationFilter(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            PrincipalDetailsService principalDetailsService,
            ObjectMapper objectMapper,
            TokenRepository tokenRepository
            ) {
        super(authenticationManager);
        this.jwtService=jwtService;
        this.principalDetailsService=principalDetailsService;
        this.objectMapper=objectMapper;
        this.tokenRepository=tokenRepository;
    }
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        String jwt = request.getHeader("Authorization");
        //jwt가 없거나 Bearer로 시작하지 않으면 거부
        if (jwt == null || !jwt.startsWith("Bearer") ) {
            System.out.println("login");
            String path = request.getContextPath() + request.getServletPath();
            if (
                    path.equals("/login/oauth2/code/kakao") ||
                            path.equals("/api/oauth2/kakao")
            ) {
                chain.doFilter(request, response);
                return;
            }
            throw new InvalidTokenException();
        }


        String token = jwt.replace("Bearer ", "");
        String tokenType = jwtService.getTokenType(token);
        Long id = jwtService.getId(token);
        request.setAttribute("id", id);

        Long id = jwtService.getId(token);
        request.setAttribute("id", id);

        // refresh token이라면 검증 후 access token과 refresh token 재생성 후 반환
        // refresh token이 만료되었다면 ExpiredJwtException 에러 발생
        if (tokenType.equals(TokenType.REFRESH.getName())) {
            // TokenRepository에 저장되어있는 refresh token을 가져와서 동일한지 검사한다.
            Optional<Token> findToken = tokenRepository.findById(id);
            // 데이터베이스에 저장된 refresh token이 없거나 일치하지 않을 때
            if (findToken.isEmpty() || !findToken.get().getRefreshToken().equals(token)) {
                throw new RefreshTokenNotSameException();
            }

            String serverId = jwtService.getServerId(token);
            String accessToken = jwtService.createAccessToken(serverId, id);
            String refreshToken = jwtService.createRefreshToken(serverId, id);
            TokenInfo tokenInfo = TokenInfo.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
            writeTokenResponse(response,"토큰을 재생성하였습니다.",tokenInfo);
            return;
        }

        // 만약 access token이었다면 검증 진행 후 성공한다면 Authentication 객체를 SecurityContextHolder에 넣음
        System.out.println("tokenType = " + tokenType);
        String serverId = jwtService.getServerId(token);
        PrincipalDetails principalDetails = (PrincipalDetails) principalDetailsService.loadUserByUsername(serverId);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principalDetails,
                null,
                principalDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("id : {} 접근 권한이 존재합니다.", serverId);
        chain.doFilter(request, response);
    }

    /**
     *
     * @param response HttpServletResponse
     * @param message 토큰 생성 메시지
     * @param result TokenInfo 객체
     */

    private void writeTokenResponse(HttpServletResponse response, String message, TokenInfo result) {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        ResultResponse res = ResultResponse.builder()
                .code(response.getStatus())
                .message(message)
                .result(result).build();
        try {
            String s = objectMapper.writeValueAsString(res);
            PrintWriter writer = response.getWriter();
            writer.write(s);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}