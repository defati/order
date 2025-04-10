package com.defati.order.mapper;

import com.defati.order.dto.ItemPedidoDTO;
import com.defati.order.entity.ItemPedido;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { ItemPedidoMapper.class })
public interface ItemPedidoMapper {

    ItemPedidoMapper INSTANCE = Mappers.getMapper(ItemPedidoMapper.class);

    ItemPedido toEntity(ItemPedidoDTO dto);

    ItemPedidoDTO toDTO(ItemPedido entity);
}