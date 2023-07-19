package com.tickettimer.backendserver.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tickettimer.backendserver.filter.AuthorizationExceptionFilter;
import com.tickettimer.backendserver.filter.JwtAuthorizationFilter;
import com.tickettimer.backendserver.service.JwtService;
import com.tickettimer.backendserver.service.PrincipalDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final ObjectMapper objectMapper;
    private final JwtService jwtService;
    private final PrincipalDetailsService principalDetailsService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.formLogin(Customizer.withDefaults());
        httpSecurity.formLogin(
                formLogin -> formLogin.disable()
        );
        httpSecurity.httpBasic(
                httpBasic -> httpBasic.disable()
        );
        httpSecurity.csrf(
                csrf -> csrf.disable()
        );
        httpSecurity.sessionManagement(
                sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        httpSecurity.apply(new MyCustom());
        httpSecurity.authorizeHttpRequests(
                request->request.requestMatchers("/api/oauth2/kakao").permitAll()
                        .requestMatchers("/login/oauth2/code/kakao").permitAll()
                        .anyRequest().authenticated()
        );
        return httpSecurity.build();
    }

    public class MyCustom extends AbstractHttpConfigurer<MyCustom, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
                    .addFilter(new JwtAuthorizationFilter(authenticationManager, jwtService, principalDetailsService, objectMapper))
                    .addFilterBefore(new AuthorizationExceptionFilter(objectMapper), JwtAuthorizationFilter.class);

        }
    }

}
