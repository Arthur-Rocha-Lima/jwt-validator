package com.arthurrocha.desafio_itau.validator;

import com.arthurrocha.desafio_itau.exception.InvalidJwtTokenException;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class SeedValidator implements ClaimValidator {

    @Override
    public void validate(Claims claims) {
        String seed = claims.get("Seed", String.class);

        if (seed == null || seed.isBlank()) {
            throw new InvalidJwtTokenException("Seed is null or blank");
        }

        try {
            BigInteger num = new BigInteger(seed);
            if (num.signum() <= 0) {
                throw new InvalidJwtTokenException("Seed is not a prime number");
            }
            if (!num.isProbablePrime(10)) {
                throw new InvalidJwtTokenException("Seed is not a prime number");
            }
        } catch (NumberFormatException e) {
            throw new InvalidJwtTokenException("Seed is not a number");
        }
    }
}
