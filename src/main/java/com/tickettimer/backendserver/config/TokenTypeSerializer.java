package com.tickettimer.backendserver.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.tickettimer.backendserver.dto.TokenType;

import java.io.IOException;

public class TokenTypeSerializer extends JsonSerializer<TokenType> {

    @Override
    public void serialize(TokenType value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        jsonGenerator.writeString(value.toString());
    }
}