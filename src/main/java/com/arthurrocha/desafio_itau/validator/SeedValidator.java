package com.arthurrocha.desafio_itau.validator;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

@Component
public class SeedValidator implements ClaimValidator {

    @Override
    public void validate(Claims claims) {
        String seed = claims.get("Seed", String.class);

        if (seed == null || !isPrimeNumber(Integer.parseInt(seed))) {
            throw new RuntimeException("Seed inválido");
        }
    }

    private boolean isPrimeNumber(int number) {
        if (number <= 1) return false;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;
    }
}
