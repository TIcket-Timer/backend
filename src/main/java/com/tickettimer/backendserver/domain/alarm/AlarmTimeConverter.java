package com.tickettimer.backendserver.domain.alarm;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class AlarmTimeConverter implements AttributeConverter<List<Integer>, String> {

    @Override
    public String convertToDatabaseColumn(List<Integer> integers) {
        return integers.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    @Override
    public List<Integer> convertToEntityAttribute(String s) {
        return Arrays.stream(s.split(","))
                .map(Integer::parseInt).collect(Collectors.toList());
    }
}
