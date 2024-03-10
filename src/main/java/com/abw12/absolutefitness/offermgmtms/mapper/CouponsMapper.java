//package com.abw12.absolutefitness.offermgmtms.mapper;
//
//import com.abw12.absolutefitness.offermgmtms.dto.CouponsDTO;
//import com.abw12.absolutefitness.offermgmtms.entity.CouponsDAO;
//import com.abw12.absolutefitness.offermgmtms.helper.OffsetDateTimeParser;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;
//
//@Mapper(componentModel = "spring")
//public interface CouponsMapper extends OffsetDateTimeParser {
//
//    CouponsMapper INSTANCE = Mappers.getMapper(CouponsMapper.class);
//
//    @Mapping(source = "startDate" , target = "startDate" , qualifiedByName = "stringToOffsetDateTime")
//    @Mapping(source = "endDate" , target = "endDate" , qualifiedByName = "stringToOffsetDateTime")
//    CouponsDAO dtoToEntity(CouponsDTO dto);
//
//    @Mapping(source = "startDate" , target = "startDate" , qualifiedByName = "offsetDateTimeToString")
//    @Mapping(source = "endDate" , target = "endDate" , qualifiedByName = "offsetDateTimeToString")
//    CouponsDTO entityToDto(CouponsDAO entity);
//}
