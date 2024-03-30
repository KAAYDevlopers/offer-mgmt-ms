package com.abw12.absolutefitness.offermgmtms.mapper;


import com.abw12.absolutefitness.offermgmtms.constants.Constants;
import com.abw12.absolutefitness.offermgmtms.dto.CouponValidationReq;
import com.abw12.absolutefitness.offermgmtms.dto.CustomerDTO;
import com.abw12.absolutefitness.offermgmtms.dto.OfferVariantDTO;

import java.util.*;

public class RequestDTOMapper {

    public static CustomerDTO mapRequestParamsToCustomerDTO(Map<String ,Object> params){
        CustomerDTO request = new CustomerDTO();
        if(params == null)
            return request;

        if(params.containsKey("couponId")){
            request.setCouponId((String)params.get("couponId"));
        }

        if(params.containsKey("userId")){
            request.setUserId((String)params.get("userId"));
        }

        if(params.containsKey("couponUsedDate")){
            request.setCouponUsedDate((String)params.get("couponUsedDate"));
        }
        return request;
    }


    public static OfferVariantDTO mapRequestParamsToOfferVariantDTO(Map<String ,Object> params){
        OfferVariantDTO request = new OfferVariantDTO();
        if(params == null)
            return request;

        if(params.containsKey(Constants.OFFER_ID))
            request.setOfferId(String.valueOf(params.get(Constants.OFFER_ID)));
        if(params.containsKey(Constants.VARIANT_ID))
            request.setVariantId(String.valueOf(params.get(Constants.VARIANT_ID)));

        return request;
    }

    public static CouponValidationReq mapRequestParamsToCouponValidationReq(Map<String ,Object> params){
        CouponValidationReq request = new CouponValidationReq();
        if(params == null)
            return request;

        if(params.containsKey("couponCode"))
            request.setCouponCode(String.valueOf(params.get(Constants.OFFER_ID)));
        if(params.containsKey("variantIds")){
            List<String> variantids = Collections.singletonList(params.get("variantIds").toString());
            request.setVariantIds(new HashSet<>(variantids));
        }

        return request;
    }
}
