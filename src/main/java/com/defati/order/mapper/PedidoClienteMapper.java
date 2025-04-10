package com.defati.order.mapper;

import com.defati.order.dto.PedidoClienteDTO;
import com.defati.order.entity.PedidoCliente;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PedidoClienteMapper {

    PedidoClienteMapper INSTANCE = Mappers.getMapper(PedidoClienteMapper.class);

    PedidoCliente toEntity(PedidoClienteDTO dto);

    PedidoClienteDTO toDTO(PedidoCliente entity);
}