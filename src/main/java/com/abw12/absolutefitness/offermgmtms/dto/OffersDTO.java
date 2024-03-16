package com.abw12.absolutefitness.offermgmtms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OffersDTO {
    private String offerId;
    private String description;
    private String discountType;
    private Integer discountValue;
    private Set<OfferVariantDTO> applicableVariantIds;
    private String startDate;
    private String endDate;
    private Set<OfferConditionDTO> conditions;
}
