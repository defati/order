package com.defati.order.pedidoFornecedorTest;

import com.defati.order.entity.PedidoCliente;
import com.defati.order.entity.PedidoFornecedor;
import com.defati.order.entity.Revenda;
import com.defati.order.entity.StatusPedidoFornecedor;
import com.defati.order.exception.RegraNegocioException;
import com.defati.order.repository.PedidoClienteRepository;
import com.defati.order.repository.PedidoFornecedorRepository;
import com.defati.order.repository.RevendaRepository;
import com.defati.order.service.PedidoFornecedorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoFornecedorServiceTest {

    @Mock
    private RevendaRepository revendaRepository;

    @Mock
    private PedidoClienteRepository pedidoClienteRepository;

    @Mock
    private PedidoFornecedorRepository pedidoFornecedorRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PedidoFornecedorService pedidoFornecedorService;

    @Test
    void deveConsolidarPedidoComSucesso() {
        UUID revendaId = UUID.randomUUID();
        Revenda revenda = new Revenda();
        revenda.setId(revendaId);

        PedidoCliente pedido1 = new PedidoCliente();
        pedido1.setTotalItens(500);

        PedidoCliente pedido2 = new PedidoCliente();
        pedido2.setTotalItens(600);

        when(revendaRepository.buscarPorId(revendaId)).thenReturn(Optional.of(revenda));
        when(pedidoClienteRepository.listarPorRevenda(revendaId)).thenReturn(List.of(pedido1, pedido2));
        when(restTemplate.postForObject(anyString(), any(), eq(String.class))).thenReturn("success");

        ResponseEntity<String> response = pedidoFornecedorService.consolidarPedido(revendaId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("✅ Pedido com 1100 itens enviado ao fornecedor com sucesso!"));
    }

    @Test
    void deveFalharQuandoQuantidadeInsuficiente() {
        UUID revendaId = UUID.randomUUID();
        Revenda revenda = new Revenda();
        revenda.setId(revendaId);

        PedidoCliente pedido = new PedidoCliente();
        pedido.setTotalItens(300);

        when(revendaRepository.buscarPorId(revendaId)).thenReturn(Optional.of(revenda));
        when(pedidoClienteRepository.listarPorRevenda(revendaId)).thenReturn(List.of(pedido));

        ResponseEntity<String> response = pedidoFornecedorService.consolidarPedido(revendaId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("❌ A quantidade total de itens (300) ainda não atingiu o mínimo"));
    }

    @Test
    void deveRetornarErroQuandoRevendaNaoExiste() {
        UUID revendaId = UUID.randomUUID();
        when(revendaRepository.buscarPorId(revendaId)).thenReturn(Optional.empty());

        assertThrows(RegraNegocioException.class, () -> {
            pedidoFornecedorService.consolidarPedido(revendaId);
        });
    }

    @Test
    void deveMarcarPedidoComoFalhaQuandoAPIExternaRetornaErro() {
        UUID revendaId = UUID.randomUUID();
        Revenda revenda = new Revenda();
        revenda.setId(revendaId);

        PedidoCliente pedido1 = new PedidoCliente();
        pedido1.setTotalItens(600);

        PedidoCliente pedido2 = new PedidoCliente();
        pedido2.setTotalItens(500);

        when(revendaRepository.buscarPorId(revendaId)).thenReturn(Optional.of(revenda));
        when(pedidoClienteRepository.listarPorRevenda(revendaId)).thenReturn(List.of(pedido1, pedido2));
        when(restTemplate.postForObject(anyString(), any(), eq(String.class))).thenReturn("erro");

        ResponseEntity<String> response = pedidoFornecedorService.consolidarPedido(revendaId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("❌ Falha ao enviar o pedido"));
    }
}
