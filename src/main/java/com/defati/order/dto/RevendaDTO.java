package com.defati.order.dto;

import com.defati.order.anotation.CNPJ;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevendaDTO {

    @CNPJ
    private String cnpj;

    @Size(min = 3, max = 100, message = "A razão social deve ter entre 3 e 100 caracteres")
    @NotBlank(message = "A razão social é obrigatória")
    @Pattern(regexp = "^[\\p{L}0-9 .,&\\-]+$", message = "A razão social contém caracteres inválidos")
    private String razaoSocial;

    @NotBlank
    private String nomeFantasia;

    @Email
    @NotBlank
    private String email;

    @NotEmpty(message = "A lista de telefones não pode estar vazia")
    @Size(min = 1, message = "Pelo menos um telefone deve ser informado")
    private List<@Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message = "Formato de telefone inválido") String> telefones;

    @NotBlank
    private String nomeContato;

    @NotEmpty
    private List<@Valid EnderecoDTO> enderecosEntrega;
}