package com.defati.order.entity;

import lombok.Data;

@Data
public class Revenda {
    private Long id;
    private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private String email;
    private String telefone;
    private String nomeContato;
    private Endereco enderecoEntrega;
}
