package com.abw12.absolutefitness.offermgmtms.converters;

import com.abw12.absolutefitness.offermgmtms.dto.CouponVariantDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonbConverter /*implements AttributeConverter<OfferCondition, String>*/ {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String convertToDatabaseColumn(CouponVariantDTO attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON writing error", e);
        }
    }

    public static CouponVariantDTO convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, CouponVariantDTO.class);
        } catch (IOException e) {
            throw new RuntimeException("JSON reading error", e);
        }
    }


}
