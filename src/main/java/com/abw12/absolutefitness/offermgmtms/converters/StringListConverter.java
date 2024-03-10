package com.abw12.absolutefitness.offermgmtms.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class StringListConverter/* implements AttributeConverter<List<String>, String>*/ {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String convertToDatabaseColumn(List<String> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON writing error", e);
        }
    }

    public static List<String> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
        } catch (IOException e) {
            throw new RuntimeException("JSON reading error", e);
        }
    }

    public static String convertListToPostgresArray(List<String> list) {
        String arrayString = list.toString(); // Converts to [element1, element2]
        arrayString = arrayString.replace("[", "{").replace("]", "}"); // Replaces brackets with curly braces
        return arrayString;
    }
}