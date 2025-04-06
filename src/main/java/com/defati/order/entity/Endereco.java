package com.defati.order.entity;

import lombok.Data;

@Data
public class Endereco {
    private String rua;
    private String numero;
    private String cidade;
    private String estado;
    private String cep;
}
