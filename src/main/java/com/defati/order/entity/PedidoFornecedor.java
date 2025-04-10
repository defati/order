package com.defati.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PedidoFornecedor {
    private UUID id;
    private UUID revendaId;
    private int tentativas;
    private String mensagemErro;
    private StatusPedidoFornecedor status;
    private int quantidadeTotalItens;
    private List<ItemPedido> itensPedidoFornecedor;

    public PedidoFornecedor() {
        this.status = StatusPedidoFornecedor.PENDENTE;
        this.tentativas = 0;
        this.mensagemErro = null;
    }

}