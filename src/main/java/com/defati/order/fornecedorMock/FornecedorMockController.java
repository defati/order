package com.defati.order.fornecedorMock;

import com.defati.order.dto.PedidoFornecedorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mock/fornecedor")
public class FornecedorMockController {

    private final FornecedorMockService fornecedorMockService;

    public FornecedorMockController(FornecedorMockService fornecedorMockService) {
        this.fornecedorMockService = fornecedorMockService;
    }

    @PostMapping("/pedido")
    public ResponseEntity<String> receberPedido(@RequestBody PedidoFornecedorDTO dto) {
        return fornecedorMockService.processarPedido(dto);
    }
}