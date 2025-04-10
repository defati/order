package com.defati.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDTO {
    @NotBlank(message = "A rua é obrigatória")
    @Size(min = 2, max = 100, message = "A rua deve ter entre 2 e 100 caracteres")
    private String rua;

    @NotBlank(message = "O número é obrigatório")
    @Pattern(regexp = "^[\\dA-Za-z\\-\\/]+$", message = "Número contém caracteres inválidos")
    private String numero;

    @NotBlank(message = "A cidade é obrigatória")
    @Size(min = 2, max = 50, message = "A cidade deve ter entre 2 e 50 caracteres")
    private String cidade;

    @NotBlank(message = "O estado é obrigatório")
    @Pattern(regexp = "^[A-Z]{2}$", message = "O estado deve ter exatamente 2 letras maiúsculas (ex: SP)")
    private String estado;

    @NotBlank(message = "O CEP é obrigatório")
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "Formato de CEP inválido (ex: 01000-000)")
    private String cep;
}
