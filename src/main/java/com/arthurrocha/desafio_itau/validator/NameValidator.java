package com.arthurrocha.desafio_itau.validator;

import com.arthurrocha.desafio_itau.exception.InvalidJwtTokenException;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

@Component
public class NameValidator implements ClaimValidator {

    @Override
    public void validate(Claims claims) {
        String name = claims.get("Name", String.class);

        if (name == null) {
            throw new InvalidJwtTokenException("Name is null");
        }

        if (name.matches(".*\\d.*")) {
            throw new InvalidJwtTokenException("Name has numbers");
        }

        if (name.length() > 256) {
            throw new InvalidJwtTokenException("Name is too big");
        }
    }
}
