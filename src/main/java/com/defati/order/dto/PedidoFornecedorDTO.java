package com.defati.order.dto;

import com.defati.order.entity.PedidoFornecedor;

import java.util.UUID;

    public class PedidoFornecedorDTO {

        private UUID revendaId;
        private int quantidadeTotalItens;

        public PedidoFornecedorDTO(UUID revendaId, int quantidadeTotalItens) {
            this.revendaId = revendaId;
            this.quantidadeTotalItens = quantidadeTotalItens;
        }

        public PedidoFornecedorDTO(PedidoFornecedor pedido) {
            this.revendaId = pedido.getRevendaId();
            this.quantidadeTotalItens = pedido.getQuantidadeTotalItens();
        }

        // Getters e Setters
        public UUID getRevendaId() {
            return revendaId;
        }

        public void setRevendaId(UUID revendaId) {
            this.revendaId = revendaId;
        }

        public int getQuantidadeTotalItens() {
            return quantidadeTotalItens;
        }

        public void setQuantidadeTotalItens(int quantidadeTotalItens) {
            this.quantidadeTotalItens = quantidadeTotalItens;
        }
    }
