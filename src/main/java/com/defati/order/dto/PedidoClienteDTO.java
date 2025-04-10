package com.defati.order.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoClienteDTO {

    @NotNull(message = "O ID da revenda é obrigatório")
    private UUID revendaId;

    @NotBlank(message = "O nome do cliente é obrigatório")
    private String nomeCliente;

    @NotEmpty(message = "O pedido deve conter ao menos um item")
    private List<ItemPedidoDTO> itens;
}