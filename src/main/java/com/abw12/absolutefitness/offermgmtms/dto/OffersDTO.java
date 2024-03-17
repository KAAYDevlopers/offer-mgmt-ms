package com.abw12.absolutefitness.offermgmtms.dto;

import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private String discountType;
    @NotNull
    private Integer discountValue;
    private Set<OfferVariantDTO> applicableVariantIds;
    private String startDate;
    private String endDate;
    @NotNull
    private Set<OfferConditionDTO> conditions;
}
