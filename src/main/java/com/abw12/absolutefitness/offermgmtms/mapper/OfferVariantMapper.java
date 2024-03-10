package com.abw12.absolutefitness.offermgmtms.mapper;

import com.abw12.absolutefitness.offermgmtms.dto.OfferVariantDTO;
import com.abw12.absolutefitness.offermgmtms.entity.OfferVariantDAO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OfferVariantMapper {
    OfferVariantMapper INSTANCE = Mappers.getMapper(OfferVariantMapper.class);

    OfferVariantDAO dtoToEntity(OfferVariantDTO dto);

    OfferVariantDTO entityToDto(OfferVariantDAO entity);

}
