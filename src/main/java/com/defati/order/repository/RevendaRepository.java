package com.defati.order.repository;

import com.defati.order.entity.Revenda;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class RevendaRepository {

    private final Map<Long, Revenda> revendas = new HashMap<>();
    private Long contadorId = 1L;

    public Revenda salvar(Revenda revenda) {
        revenda.setId(contadorId++);
        revendas.put(revenda.getId(), revenda);
        return revenda;
    }

    public Optional<Revenda> buscarPorId(Long id) {
        return Optional.ofNullable(revendas.get(id));
    }

    public List<Revenda> listarTodas() {
        return new ArrayList<>(revendas.values());
    }
}