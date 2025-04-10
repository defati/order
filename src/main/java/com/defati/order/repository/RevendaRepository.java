package com.defati.order.repository;

import com.defati.order.entity.Revenda;
import com.defati.order.exception.RegraNegocioException;
import org.springframework.stereotype.Repository;

import java.util.*;

// TODO: 07/04/2025 tratar exceções com @ControllerAvice 
@Repository
public class RevendaRepository {

    private final Map<UUID, Revenda> revendas = new HashMap<>();

    public Revenda salvar(Revenda revenda) {
        revenda.setId(UUID.randomUUID());
        revendas.put(revenda.getId(), revenda);
        return revenda;
    }

    public Optional<Revenda> buscarPorId(UUID id) {
        return Optional.ofNullable(revendas.get(id));
    }

    public List<Revenda> listarTodas() {
        return new ArrayList<>(revendas.values());
    }

    public Revenda atualizar(UUID id, Revenda revendaAtualizada) {
        if (!revendas.containsKey(id)) {
            throw new RegraNegocioException("Revenda não encontrada para atualizar");
        }
        revendaAtualizada.setId(id);
        revendas.put(id, revendaAtualizada);
        return revendaAtualizada;
    }

    public void deletar(UUID id) {
        if (!revendas.containsKey(id)) {
            throw new RegraNegocioException("Revenda não encontrada para excluir");
        }
        revendas.remove(id);
    }
}