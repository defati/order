package com.defati.order.repository;

import com.defati.order.entity.PedidoCliente;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PedidoClienteRepository {
    private final Map<UUID, PedidoCliente> pedidos = new HashMap<>();

    public PedidoCliente salvar(PedidoCliente pedido) {
        pedidos.put(pedido.getId(), pedido);
        return pedido;
    }

    public PedidoCliente atualizar(UUID id, PedidoCliente pedidoAtualizado) {
        pedidos.put(id, pedidoAtualizado);
        return pedidoAtualizado;
    }

    public List<PedidoCliente> listarPorRevenda(UUID revendaId) {
        return pedidos.values().stream()
                .filter(p -> p.getRevendaId().equals(revendaId))
                .toList();
    }

    public Optional<PedidoCliente> buscarPorId(UUID id) {
        return Optional.ofNullable(pedidos.get(id));
    }

    public void deletarPorId(UUID id) {
        pedidos.remove(id);
    }

}
