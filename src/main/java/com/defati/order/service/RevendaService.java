package com.defati.order.service;

import com.defati.order.dto.RevendaDTO;
import com.defati.order.entity.Revenda;
import com.defati.order.exception.RegraNegocioException;
import com.defati.order.mapper.RevendaMapper;
import com.defati.order.repository.RevendaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RevendaService {

    private final RevendaRepository revendaRepository;
    private final RevendaMapper revendaMapper;
    public RevendaService(RevendaRepository repository, RevendaMapper revendaMapper) {
        this.revendaRepository = repository;
        this.revendaMapper = revendaMapper;
    }

    public List<Revenda> listarTodas() {
        return revendaRepository.listarTodas();
    }

    public Revenda buscarPorId(UUID id) {
        return revendaRepository.buscarPorId(id)
                .orElseThrow(() -> new RegraNegocioException("Revenda não encontrada"));
    }

    public Revenda cadastrar(RevendaDTO dto) {
        Revenda revenda = revendaMapper.toEntity(dto);
        return revendaRepository.salvar(revenda);
    }

    public Revenda atualizar(UUID id, RevendaDTO dto) {
        Revenda existente = buscarPorId(id);
        Revenda atualizada = revendaMapper.toEntity(dto);
        atualizada.setId(id);
        return revendaRepository.atualizar(id, atualizada);
    }


    public void deletar(UUID id) {
        buscarPorId(id);
        revendaRepository.deletar(id);
    }
}