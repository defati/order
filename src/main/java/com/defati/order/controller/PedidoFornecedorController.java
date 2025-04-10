package com.defati.order.controller;

import com.defati.order.service.PedidoFornecedorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/pedido-fornecedor")
public class PedidoFornecedorController {

    private final PedidoFornecedorService pedidoFornecedorService;

    public PedidoFornecedorController(PedidoFornecedorService pedidoFornecedorService) {
        this.pedidoFornecedorService = pedidoFornecedorService;
    }

    @GetMapping("/enviar-pedido/{revendaId}")
    public ResponseEntity<String> enviarPedido(@PathVariable UUID revendaId) {
        return  pedidoFornecedorService.consolidarPedido(revendaId);
        //return ResponseEntity.ok(resultado);
    }

}