package com.abw12.absolutefitness.offermgmtms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalcOfferRequest {

    @NotEmpty
    private String offerId;
    @NotBlank
    private String categoryName;
    @NotBlank
    private String brandName;
    @NotBlank
    private String productName;
    @NotBlank
    private String variantName;
    @NotNull
    private BigDecimal buyPrice;
}
