package com.arthurrocha.desafio_itau.validator;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

@Component
public class NameValidator implements ClaimValidator {

    @Override
    public void validate(Claims claims) {
        String name = claims.get("Name", String.class);

        if (name == null) {
            throw new RuntimeException("Name is null");
        }

        if (name.matches(".*\\d.*")) {
            throw new RuntimeException("Name has numbers");
        }

        if (name.length() > 256) {
            throw new RuntimeException("Name is to big");
        }
    }
}
