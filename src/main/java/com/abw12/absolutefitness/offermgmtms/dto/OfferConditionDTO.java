package com.abw12.absolutefitness.offermgmtms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferConditionDTO
{
    private String id;
    private String categoryName;
    private String brandName;
    private String productName;
    private String variantName;
}
