package com.defati.order.service;

import com.defati.order.dto.PedidoFornecedorDTO;
import com.defati.order.entity.PedidoCliente;
import com.defati.order.entity.PedidoFornecedor;
import com.defati.order.entity.Revenda;
import com.defati.order.entity.StatusPedidoFornecedor;
import com.defati.order.exception.FornecedorException;
import com.defati.order.exception.RegraNegocioException;
import com.defati.order.repository.PedidoClienteRepository;
import com.defati.order.repository.PedidoFornecedorRepository;
import com.defati.order.repository.RevendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class PedidoFornecedorService {

    private static final int QUANTIDADE_MINIMA_ITENS = 1000;

    private final PedidoFornecedorRepository pedidoFornecedorRepository;
    private final PedidoClienteRepository pedidoClienteRepository;
    private final RevendaRepository revendaRepository;
    private final RestTemplate restTemplate;

    public PedidoFornecedorService(PedidoFornecedorRepository pedidoFornecedorRepository,
                                   PedidoClienteRepository pedidoClienteRepository,
                                   RevendaRepository revendaRepository,
                                   RestTemplate restTemplate) {
        this.pedidoFornecedorRepository = pedidoFornecedorRepository;
        this.pedidoClienteRepository = pedidoClienteRepository;
        this.revendaRepository = revendaRepository;
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> consolidarPedido(UUID revendaId) {
        Revenda revenda = revendaRepository.buscarPorId(revendaId)
                .orElseThrow(() -> new RegraNegocioException("Revenda não encontrada."));

        List<PedidoCliente> pedidos = pedidoClienteRepository.listarPorRevenda(revendaId);

        if (pedidos.isEmpty()) {
            return ResponseEntity.status(404).body("Nenhum pedido encontrado para esta revenda.");
        }

        int totalItens = pedidos.stream()
                .mapToInt(PedidoCliente::getTotalItens)
                .sum();

        if (totalItens < QUANTIDADE_MINIMA_ITENS) {
            return ResponseEntity.status(400).body("❌ A quantidade total de itens (" + totalItens +
                    ") ainda não atingiu o mínimo de " + QUANTIDADE_MINIMA_ITENS + " unidades.");
        }

        PedidoFornecedor pedidoFornecedor = new PedidoFornecedor();
        pedidoFornecedor.setId(UUID.randomUUID());
        pedidoFornecedor.setRevendaId(revendaId);
        pedidoFornecedor.setQuantidadeTotalItens(totalItens);
        pedidoFornecedor.setStatus(StatusPedidoFornecedor.PENDENTE);
        pedidoFornecedor.setTentativas(0);

        pedidoFornecedorRepository.salvar(pedidoFornecedor);

        ResponseEntity<String> resposta = enviarPedido(pedidoFornecedor);
        String mensagem = resposta.getBody();

        if (resposta.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok("✅ Pedido com " + totalItens + " itens enviado ao fornecedor com sucesso!");
        } else {
            return ResponseEntity.status(500).body("❌ " + mensagem);
        }
    }

    public ResponseEntity<String> enviarPedido(PedidoFornecedor pedido) {
        return tentarEnviarComRetry(pedido);
    }

    private ResponseEntity<String> tentarEnviarComRetry(PedidoFornecedor pedido) {
        if (pedido.getQuantidadeTotalItens() < QUANTIDADE_MINIMA_ITENS) {
            throw new RegraNegocioException("Pedido não possui a quantidade mínima de itens para envio.");
        }

        try {
            String url = "http://localhost:8080/mock/fornecedor/pedido";

            PedidoFornecedorDTO pedidoFornecedorDTO = new PedidoFornecedorDTO(pedido);

            String response = restTemplate.postForObject(url, pedidoFornecedorDTO, String.class);


            if ("success".equalsIgnoreCase(response)) {
                pedido.setStatus(StatusPedidoFornecedor.ENVIADO);
                pedidoFornecedorRepository.salvar(pedido);
                return ResponseEntity.ok("Pedido enviado com sucesso!");
            } else {
                pedido.setStatus(StatusPedidoFornecedor.FALHA);
                pedido.setMensagemErro("Falha ao enviar o pedido: " + response);
                pedidoFornecedorRepository.salvar(pedido);
                return ResponseEntity.status(500).body("Falha ao enviar o pedido: " + response);
            }

        } catch (Exception e) {
            pedido.setTentativas(pedido.getTentativas() + 1);
            pedido.setMensagemErro(e.getMessage());

            if (pedido.getTentativas() < 3) {
                CompletableFuture.delayedExecutor(5, TimeUnit.SECONDS).execute(() -> {
                    ResponseEntity<String> responseEntity = tentarEnviarComRetry(pedido);
                    // Atualizar status conforme resposta
                    pedido.setStatus(responseEntity.getStatusCode().is2xxSuccessful() ?
                            StatusPedidoFornecedor.ENVIADO : StatusPedidoFornecedor.FALHA);
                    pedidoFornecedorRepository.salvar(pedido);
                });
                return ResponseEntity.status(500).body("Tentativa de reenvio agendada.");
            } else {
                pedido.setStatus(StatusPedidoFornecedor.FALHA);
                pedidoFornecedorRepository.salvar(pedido);
                return ResponseEntity.status(500).body("Falha definitiva após 3 tentativas.");
            }
        }
    }

    public PedidoFornecedor buscarPorId(UUID id) {
        return pedidoFornecedorRepository.buscarPorId(id)
                .orElseThrow(() -> new RegraNegocioException("Pedido não encontrado."));
    }
}