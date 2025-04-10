package com.defati.order.entity;

import com.defati.order.dto.EnderecoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Revenda {
    private UUID id;
    private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private String email;
    private List<String> telefones;
    private String nomeContato;
    private List<EnderecoDTO> enderecosEntrega = new ArrayList<>();
}
