package com.defati.order.controller;

import com.defati.order.dto.PedidoClienteDTO;
import com.defati.order.entity.PedidoCliente;
import com.defati.order.service.PedidoClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/revendas/{revendaId}/pedidos")
@Tag(name = "PedidoClienteController", description = "Gerencia os pedidos dos clientes por revenda")
public class PedidoClienteController {

    private final PedidoClienteService service;

    public PedidoClienteController(PedidoClienteService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Cria um novo pedido de cliente")
    @ApiResponse(responseCode = "200", description = "Pedido criado com sucesso")
    public ResponseEntity<PedidoCliente> criarPedido(@PathVariable UUID revendaId,
                                                     @RequestBody @Valid PedidoClienteDTO dto) {
        return ResponseEntity.ok(service.criarPedido(revendaId, dto));
    }

    @GetMapping
    public ResponseEntity<List<PedidoCliente>> listarPedidos(@PathVariable UUID revendaId) {
        return ResponseEntity.ok(service.listarPedidosPorRevenda(revendaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoCliente> buscarPedido(@PathVariable UUID revendaId,
                                                      @PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPedidoPorId(revendaId, id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoCliente> atualizarPedido(@PathVariable UUID revendaId,
                                                         @PathVariable UUID id,
                                                         @RequestBody @Valid PedidoClienteDTO dto) {
        return ResponseEntity.ok(service.atualizarPedido(revendaId, id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable UUID revendaId,
                                              @PathVariable UUID id) {
        service.deletarPedido(revendaId, id);
        return ResponseEntity.noContent().build();
    }
}