package com.arthurrocha.desafio_itau.validator;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

@Component
public class SeedValidator implements ClaimValidator {

    @Override
    public void validate(Claims claims) {
        String seed = claims.get("Seed", String.class);

        if (seed == null || seed.isBlank()) {
            throw new RuntimeException("Seed is null or blank");
        }

        try {
            int num = Integer.parseInt(seed);
            if (!isPrimeNumber(num)) {
                throw new RuntimeException("Seed is not a prime number");
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("Seed is not a number");
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
