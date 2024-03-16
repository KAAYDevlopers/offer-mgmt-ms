package com.abw12.absolutefitness.offermgmtms.mapper;


import com.abw12.absolutefitness.offermgmtms.dto.CustomerDTO;

import java.util.Map;

public class CustomerDTOMapper {

    public static CustomerDTO mapRequestParamsToDTO(Map<String ,Object> params){
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
}
