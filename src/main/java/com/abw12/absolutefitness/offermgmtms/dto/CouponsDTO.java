package com.abw12.absolutefitness.offermgmtms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponsDTO {

    private String couponId;
    private String description;
    @NotNull
    private String discountType;
    @NotNull
    private Integer discountValue;
    private Double minOrderValue;
    private String couponCode;
    @NotNull
    private Integer usageLimit;
    @NotNull
    private Integer usagePerUser;
    private Boolean isActive;
    private Set<CouponVariantDTO> applicableVariantIds;
    private String startDate;
    private String endDate;
}
