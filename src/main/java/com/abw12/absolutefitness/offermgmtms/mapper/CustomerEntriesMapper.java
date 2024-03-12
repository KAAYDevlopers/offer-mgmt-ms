package com.abw12.absolutefitness.offermgmtms.mapper;

import com.abw12.absolutefitness.offermgmtms.dto.CustomerDTO;
import com.abw12.absolutefitness.offermgmtms.entity.CustomerDAO;
import com.abw12.absolutefitness.offermgmtms.helper.OffsetDateTimeParser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerEntriesMapper extends OffsetDateTimeParser {

    CustomerEntriesMapper INSTANCE = Mappers.getMapper(CustomerEntriesMapper.class);

    @Mapping(source = "couponUsedDate",target = "couponUsedDate" ,qualifiedByName = "stringToOffsetDateTime")
    CustomerDAO dtoToEntity(CustomerDTO dto);
    @Mapping(source = "couponUsedDate",target = "couponUsedDate" ,qualifiedByName = "offsetDateTimeToString")
    CustomerDTO entityToDto(CustomerDAO entity);
}
