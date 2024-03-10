package com.abw12.absolutefitness.offermgmtms.mapper;

import com.abw12.absolutefitness.offermgmtms.dto.OfferConditionDTO;
import com.abw12.absolutefitness.offermgmtms.entity.OfferConditionDAO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OfferConditionMapper {

    OfferConditionMapper INSTANCE = Mappers.getMapper(OfferConditionMapper.class);

    OfferConditionDAO dtoToEntity(OfferConditionDTO dto);

    OfferConditionDTO entityToDto(OfferConditionDAO entity);
}
