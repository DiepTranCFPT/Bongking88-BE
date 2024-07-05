package com.example.demo.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public LocalTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String timeStr = jsonParser.getValueAsString();
        try {
            return LocalTime.parse(timeStr, formatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid LocalTime format. Expected HH:mm:ss.");
        }
    }
}
