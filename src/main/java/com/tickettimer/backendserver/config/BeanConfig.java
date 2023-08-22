package com.tickettimer.backendserver.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.tickettimer.backendserver.dto.TokenType;
import org.antlr.v4.runtime.Token;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.format.DateTimeFormatter;

@Configuration
public class BeanConfig {
//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        SimpleModule simpleModule = new SimpleModule();
//        simpleModule.addSerializer(TokenType.class, new TokenTypeSerializer()); // YourEnumSerializer는 Enum을 String으로 직렬화하는 커스텀 Serializer 클래스입니다.
//        objectMapper.registerModule(simpleModule);
//
//        // Enum 값을 String에서 Enum으로 역직렬화하기 위한 설정
//        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
//        return objectMapper;
//    }
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {

        return builder -> {
            // LocalDate 타입 직렬화, 역직렬화 패턴
            DateTimeFormatter localDateSerializeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter localDateDeserializeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

            // LocalDateTime 타입 직렬화, 역직렬화 패턴
            DateTimeFormatter localDateTimeSerializeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
            DateTimeFormatter localDateTimeDeserializeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

            // 등록
            builder.serializers(new LocalDateSerializer(localDateSerializeFormatter));
            builder.deserializers(new LocalDateDeserializer(localDateDeserializeFormatter));

            builder.serializers(new LocalDateTimeSerializer(localDateTimeSerializeFormatter));
            builder.deserializers(new LocalDateTimeDeserializer(localDateTimeDeserializeFormatter));
        };
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
