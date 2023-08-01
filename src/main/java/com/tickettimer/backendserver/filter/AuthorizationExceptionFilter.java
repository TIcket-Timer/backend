package com.tickettimer.backendserver.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tickettimer.backendserver.dto.ResultResponse;
import com.tickettimer.backendserver.dto.TokenInfo;
import com.tickettimer.backendserver.exception.CustomNotFoundException;
import com.tickettimer.backendserver.exception.InvalidTokenException;
import com.tickettimer.backendserver.exception.RefreshTokenNotSameException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

public class AuthorizationExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    public AuthorizationExceptionFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
            String accessToken = (String) request.getAttribute("access");
            String refreshToken = (String) request.getAttribute("refresh");
            TokenInfo tokenInfo = TokenInfo.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
//            writeErrorResponse(response, HttpStatus.OK.value(), "토큰 재생성 완료했습니다.", tokenInfo);
        } catch (CustomNotFoundException ex) {
            writeErrorResponse(response, HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);
        } catch (InvalidTokenException ex) {
            writeErrorResponse(response, HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
        } catch (ExpiredJwtException ex) {
            writeErrorResponse(response, HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), null);
        } catch (RefreshTokenNotSameException ex) {
            writeErrorResponse(response, HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), null);
        }
    }
    private void writeErrorResponse(HttpServletResponse response, int status, String message, Object result) {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        ResultResponse res = ResultResponse.builder()
                .code(status)
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
