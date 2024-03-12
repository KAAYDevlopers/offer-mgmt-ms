package com.abw12.absolutefitness.offermgmtms.helper;

import com.abw12.absolutefitness.offermgmtms.dto.CouponValidationReq;
import com.abw12.absolutefitness.offermgmtms.dto.CouponVariantDTO;
import com.abw12.absolutefitness.offermgmtms.dto.CouponsDTO;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Utils {

    public Set<CouponVariantDTO> filterApplicableVariants(CouponsDTO couponsDTO, CouponValidationReq req){
        Set<CouponVariantDTO> applicableVariantIds=null;
        if(!req.getVariantIds().isEmpty()){
            applicableVariantIds = req.getVariantIds().stream().flatMap(variantId ->
                    couponsDTO.getApplicableVariantIds().stream().filter(couponVariantDTO -> couponVariantDTO.getVariantId().equalsIgnoreCase(variantId))
            ).collect(Collectors.toSet());
        }
        return applicableVariantIds;
    }

    public boolean checkIfCouponIsApplicable(CouponsDTO couponsDTO){
        return couponsDTO.getIsActive() && couponsDTO.getUsageLimit() > 0;
    }

}
