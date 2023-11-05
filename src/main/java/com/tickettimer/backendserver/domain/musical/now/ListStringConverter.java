package com.tickettimer.backendserver.domain.musical.now;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Converter
public class ListStringConverter implements AttributeConverter<List<String>, String> {
    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        String collect = attribute.stream().map(String::valueOf).collect(Collectors.joining(","));
        return collect;
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        return Arrays.stream(s.split(","))
                .map(String::trim).collect(Collectors.toList());
    }
}
