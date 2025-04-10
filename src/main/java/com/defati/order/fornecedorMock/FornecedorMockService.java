package com.defati.order.fornecedorMock;

import com.defati.order.dto.PedidoFornecedorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FornecedorMockService {

    public ResponseEntity<String> processarPedido(PedidoFornecedorDTO dto) {
        System.out.println("📦 Pedido recebido pelo fornecedor: " + dto);
        return ResponseEntity.ok("✅ Pedido processado com sucesso pelo fornecedor!");
    }
}