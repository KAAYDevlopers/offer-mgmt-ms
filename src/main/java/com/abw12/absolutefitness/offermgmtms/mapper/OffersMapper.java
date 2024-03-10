package com.abw12.absolutefitness.offermgmtms.mapper;

import com.abw12.absolutefitness.offermgmtms.dto.OffersDTO;
import com.abw12.absolutefitness.offermgmtms.entity.OffersDAO;
import com.abw12.absolutefitness.offermgmtms.helper.OffsetDateTimeParser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OffersMapper extends OffsetDateTimeParser {

    OffersMapper INSTANCE = Mappers.getMapper(OffersMapper.class);

    @Mapping(source = "startDate",target = "startDate" ,qualifiedByName = "stringToOffsetDateTime")
    @Mapping(source = "endDate",target = "endDate" ,qualifiedByName = "stringToOffsetDateTime")
    OffersDAO dtoToEntity(OffersDTO dto);

    @Mapping(source = "startDate",target = "startDate" ,qualifiedByName = "offsetDateTimeToString")
    @Mapping(source = "endDate",target = "endDate" ,qualifiedByName = "offsetDateTimeToString")
    OffersDTO entityToDto(OffersDAO entity);
}
