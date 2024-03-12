package com.abw12.absolutefitness.offermgmtms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class CouponValidationRes {

    private CouponsDTO couponData;
    private Set<CouponVariantDTO> applicableToRequestedVariantIds;
    private String msg;
    private String statusCode;
}
