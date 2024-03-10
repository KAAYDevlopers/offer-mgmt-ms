package com.abw12.absolutefitness.offermgmtms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponsDTO {

    private String couponId;
    private String description;
    private String discountType;
    private Integer discountValue;
//    private List<String> applicableVariantIds;
//    private List<String> applicableCategories;
    private String startDate;
    private String endDate;
    private OfferConditionDTO conditions;
    private String couponCode;
    private Integer usageLimit;
    private Integer usagePerUser;
    private Boolean isActive;
}
