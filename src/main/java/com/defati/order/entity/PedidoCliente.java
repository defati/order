package com.defati.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoCliente {
    private UUID id;
    private UUID revendaId;
    private String nomeCliente;
    private List<ItemPedido> itens;

    public int getTotalItens() {
        if (itens == null) return 0;
        return itens.stream()
                .mapToInt(ItemPedido::getQuantidade)
                .sum();
    }

    public void setTotalItens(int i) {
    }
}