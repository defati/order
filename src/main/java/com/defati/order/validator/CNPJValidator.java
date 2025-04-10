package com.defati.order.validator;

import com.defati.order.anotation.CNPJ;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CNPJValidator implements ConstraintValidator<CNPJ, String> {

    @Override
    public boolean isValid(String cnpj, ConstraintValidatorContext context) {
        if (cnpj == null) {
            return false;
        }

        // 🧹 Remove pontos, traços e barra (máscara)
        cnpj = cnpj.replaceAll("\\D", "");

        if (cnpj.length() != 14 || !cnpj.matches("\\d+")) {
            return false;
        }

        int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        try {
            int soma = 0;
            for (int i = 0; i < 12; i++) {
                soma += Character.getNumericValue(cnpj.charAt(i)) * pesos1[i];
            }
            int dig1 = soma % 11 < 2 ? 0 : 11 - (soma % 11);

            soma = 0;
            for (int i = 0; i < 13; i++) {
                soma += Character.getNumericValue(cnpj.charAt(i)) * pesos2[i];
            }
            int dig2 = soma % 11 < 2 ? 0 : 11 - (soma % 11);

            return dig1 == Character.getNumericValue(cnpj.charAt(12)) &&
                    dig2 == Character.getNumericValue(cnpj.charAt(13));
        } catch (Exception e) {
            return false;
        }
    }
}