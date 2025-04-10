package com.defati.order.mapper;

import com.defati.order.dto.RevendaDTO;
import com.defati.order.entity.Revenda;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RevendaMapper {

    RevendaMapper INSTANCE = Mappers.getMapper(RevendaMapper.class);

    Revenda toEntity(RevendaDTO dto);

    RevendaDTO toDTO(Revenda entity);
}
