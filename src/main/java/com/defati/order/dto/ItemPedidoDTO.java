package com.defati.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoDTO {
    @NotBlank(message = "Nome do produto é obrigatório")
    private String nomeProduto;

    @Min(value = 1, message = "Quantidade mínima é 1")
    private int quantidade;
}