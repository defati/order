package com.defati.order.repository;

import com.defati.order.entity.PedidoFornecedor;
import com.defati.order.entity.StatusPedidoFornecedor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PedidoFornecedorRepository {

    private final Map<UUID, PedidoFornecedor> envios = new HashMap<>();

    public PedidoFornecedor salvar(PedidoFornecedor envio) {
        if (envio.getId() == null) {
            envio.setId(UUID.randomUUID());
        }
        envios.put(envio.getId(), envio);
        return envio;
    }



    public Optional<PedidoFornecedor> buscarPorId(UUID id) {
        return Optional.ofNullable(envios.get(id));
    }

    public List<PedidoFornecedor> listarTodos() {
        return new ArrayList<>(envios.values());
    }

    public List<PedidoFornecedor> findAllByStatus(StatusPedidoFornecedor status) {
        return envios.values().stream()
                .filter(p -> p.getStatus() == status)
                .toList();
    }

    public void deletar(UUID id) {
        envios.remove(id);
    }
}