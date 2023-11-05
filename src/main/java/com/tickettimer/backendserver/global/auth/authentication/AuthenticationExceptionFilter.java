package com.tickettimer.backendserver.global.auth.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tickettimer.backendserver.global.dto.ResultResponse;
import feign.FeignException;
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

public class AuthenticationExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    public AuthenticationExceptionFilter(ObjectMapper objectMapper) {
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
        } catch (FeignException.Unauthorized ex) {
            writeErrorResponse(response, HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), null);
        } catch (ExpiredJwtException ex) {
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
