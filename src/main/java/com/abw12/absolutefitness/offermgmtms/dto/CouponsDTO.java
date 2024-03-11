package com.abw12.absolutefitness.offermgmtms.dto;

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
    private String discountType;
    private Integer discountValue;
    private Double minOrderValue;
    private String couponCode;
    private Integer usageLimit;
    private Integer usagePerUser;
    private Boolean isActive;
    private Set<CouponVariantDTO> applicableVariantIds;
    private String startDate;
    private String endDate;
}
