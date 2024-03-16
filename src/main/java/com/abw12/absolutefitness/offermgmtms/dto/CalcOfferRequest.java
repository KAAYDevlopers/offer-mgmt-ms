package com.abw12.absolutefitness.offermgmtms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalcOfferRequest {

    private String offerId;
    private String categoryName;
    private String brandName;
    private String productName;
    private String variantName;
    private BigDecimal buyPrice;
}
