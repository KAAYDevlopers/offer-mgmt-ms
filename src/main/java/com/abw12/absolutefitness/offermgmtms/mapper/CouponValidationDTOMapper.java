package com.abw12.absolutefitness.offermgmtms.mapper;


import com.abw12.absolutefitness.offermgmtms.dto.CouponValidationReq;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CouponValidationDTOMapper {

    public static CouponValidationReq mapRequestParamsToDTO(Map<String ,Object> params){
        CouponValidationReq request = new CouponValidationReq();
        if(params == null)
            return request;

        if(params.containsKey("couponId")){
            request.setCouponCode((String)params.get("couponId"));
        }

        if (params.containsKey("variantIds") && params.get("variantIds") instanceof List) {
            List<?> variantIdList = (List<?>) params.get("variantIds");
            Set<String> variantIdsSet = new HashSet<>();
            for (Object id : variantIdList) {
                if (id instanceof String) {
                    variantIdsSet.add((String) id);
                }
            }
            request.setVariantIds(variantIdsSet);
        }
        return request;
    }
}
