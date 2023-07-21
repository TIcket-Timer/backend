package com.tickettimer.backendserver.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.tickettimer.backendserver.dto.TokenType;
import org.antlr.v4.runtime.Token;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(TokenType.class, new TokenTypeSerializer()); // YourEnumSerializer는 Enum을 String으로 직렬화하는 커스텀 Serializer 클래스입니다.
        objectMapper.registerModule(simpleModule);

        // Enum 값을 String에서 Enum으로 역직렬화하기 위한 설정
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        return objectMapper;
    }
}
