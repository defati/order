package com.defati.order.service;

import com.defati.order.dto.PedidoClienteDTO;
import com.defati.order.entity.ItemPedido;
import com.defati.order.entity.PedidoCliente;
import com.defati.order.exception.RegraNegocioException;
import com.defati.order.repository.PedidoClienteRepository;
import com.defati.order.repository.RevendaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PedidoClienteService {

    private final PedidoClienteRepository pedidoClienteRepository;
    private final RevendaRepository revendaRepository;

    public PedidoClienteService(PedidoClienteRepository pedidoClienteRepository, RevendaRepository revendaRepository){
        this.pedidoClienteRepository = pedidoClienteRepository;
        this.revendaRepository = revendaRepository;
    }

    public PedidoCliente criarPedido(UUID revendaId, PedidoClienteDTO dto) {
        validarRevendaExiste(revendaId);

        PedidoCliente pedido = new PedidoCliente();
        pedido.setId(UUID.randomUUID());
        pedido.setNomeCliente(dto.getNomeCliente());
        pedido.setRevendaId(revendaId);

        List<ItemPedido> itens = dto.getItens().stream()
                .map(i -> new ItemPedido(i.getNomeProduto(), i.getQuantidade()))
                .toList();
        pedido.setItens(itens);

        return pedidoClienteRepository.salvar(pedido);
    }

    public List<PedidoCliente> listarPedidosPorRevenda(UUID revendaId) {
        validarRevendaExiste(revendaId);
        return pedidoClienteRepository.listarPorRevenda(revendaId);
    }

    public PedidoCliente buscarPedidoPorId(UUID revendaId, UUID id) {
        validarRevendaExiste(revendaId);
        PedidoCliente pedido = pedidoClienteRepository.buscarPorId(id)
                .orElseThrow(() -> new RegraNegocioException("Pedido não encontrado"));

        if (!pedido.getRevendaId().equals(revendaId)) {
            throw new RegraNegocioException("Pedido não pertence à revenda informada");
        }

        return pedido;
    }

    public PedidoCliente atualizarPedido(UUID revendaId, UUID id, PedidoClienteDTO dto) {
        PedidoCliente existente = buscarPedidoPorId(revendaId, id);
        existente.setNomeCliente(dto.getNomeCliente());

        List<ItemPedido> itens = dto.getItens().stream()
                .map(i -> new ItemPedido(i.getNomeProduto(), i.getQuantidade()))
                .toList();
        existente.setItens(itens);

        return pedidoClienteRepository.atualizar(id, existente);
    }

    public void deletarPedido(UUID revendaId, UUID id) {
        buscarPedidoPorId(revendaId, id); // já valida tudo
        pedidoClienteRepository.deletarPorId(id);
    }

    private void validarRevendaExiste(UUID revendaId) {
        revendaRepository.buscarPorId(revendaId)
                .orElseThrow(() -> new RegraNegocioException("Revenda não encontrada"));
    }
}