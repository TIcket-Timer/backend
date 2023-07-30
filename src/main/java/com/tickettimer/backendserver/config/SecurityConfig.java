package com.tickettimer.backendserver.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tickettimer.backendserver.filter.AuthenticationExceptionFilter;
import com.tickettimer.backendserver.filter.AuthorizationExceptionFilter;
import com.tickettimer.backendserver.filter.JwtAuthenticationFilter;
import com.tickettimer.backendserver.filter.JwtAuthorizationFilter;
import com.tickettimer.backendserver.service.JwtService;
import com.tickettimer.backendserver.service.MemberService;
import com.tickettimer.backendserver.service.PrincipalDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final ObjectMapper objectMapper;
    private final JwtService jwtService;
    private final PrincipalDetailsService principalDetailsService;
    private final MemberService memberService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Value("${oauth2.member.kakao.password}")
    private String password;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
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
//        httpSecurity.apply(new MyCustom());
//        httpSecurity.authorizeHttpRequests(
//                request -> request.requestMatchers("/api/oauth2/kakao").permitAll()
//                        .requestMatchers("/login/oauth2/code/kakao").permitAll()
//                        .anyRequest().authenticated()
//        );
        return httpSecurity.build();
    }

    public class MyCustom extends AbstractHttpConfigurer<MyCustom, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(
                    authenticationManager,
                    memberService,
                    objectMapper,
                    jwtService,
                    bCryptPasswordEncoder,
                    password
            );
            jwtAuthenticationFilter.setFilterProcessesUrl("/api/oauth2/kakao");
            http
                    .addFilter(jwtAuthenticationFilter)
                    .addFilterBefore(new AuthenticationExceptionFilter(objectMapper), JwtAuthenticationFilter.class)
                    .addFilter(
                    new JwtAuthorizationFilter(
                            authenticationManager,
                            jwtService,
                            principalDetailsService,
                            objectMapper)
            )
                    .addFilterBefore(new AuthorizationExceptionFilter(objectMapper), JwtAuthorizationFilter.class);

        }
    }

}
