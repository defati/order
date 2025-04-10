package com.defati.order.controller;

import com.defati.order.dto.RevendaDTO;
import com.defati.order.entity.Revenda;
import com.defati.order.service.RevendaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/revendas")
public class RevendaController {

    private final RevendaService revendaService;

    public RevendaController(RevendaService revendaService) {
        this.revendaService = revendaService;
    }

    @PostMapping
    public ResponseEntity<Revenda> cadastrar(@Valid @RequestBody RevendaDTO dto) {
        return ResponseEntity.ok(revendaService.cadastrar(dto));
    }

    @GetMapping
    public ResponseEntity<List<Revenda>> listarTodas() {
        return ResponseEntity.ok(revendaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Revenda> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(revendaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Revenda> atualizar(@PathVariable UUID id,
                                             @Valid @RequestBody RevendaDTO dto) {
        return ResponseEntity.ok(revendaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        revendaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}