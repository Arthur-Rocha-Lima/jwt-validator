package com.arthurrocha.desafio_itau.validator;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

@Component
public class NameValidator implements ClaimValidator {

    @Override
    public void validate(Claims claims) {
        String name = claims.get("Name", String.class);

        if (name == null || name.matches(".*\\d.*")) {
            throw new RuntimeException("Name inválido");
        }

        if (name.length() > 256) {
            throw new RuntimeException("Name muito grande");
        }
    }
}
