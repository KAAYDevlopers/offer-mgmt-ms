package com.abw12.absolutefitness.offermgmtms.mapper;

import com.abw12.absolutefitness.offermgmtms.dto.CouponVariantDTO;
import com.abw12.absolutefitness.offermgmtms.entity.CouponVariantDAO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CouponVariantMapper {

    CouponVariantMapper INSTANCE = Mappers.getMapper(CouponVariantMapper.class);

    CouponVariantDAO dtoToEntity(CouponVariantDTO dto);

    CouponVariantDTO entityToDto(CouponVariantDAO entity);
}
