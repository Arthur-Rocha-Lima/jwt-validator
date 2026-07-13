package com.arthurrocha.desafio_itau.service;

import com.arthurrocha.desafio_itau.enums.Role;
import com.arthurrocha.desafio_itau.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class JwtValidationService {

    public boolean validateToken(String token) {
        try {
            String unsignedToken = JwtUtils.getUnsignedToken(token);

            Jwt<Header, Claims> jwt = Jwts.parserBuilder()
                    .build()
                    .parseClaimsJwt(unsignedToken);

            Claims claims = jwt.getBody();

            validateClaims(claims);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void validateClaims(Claims claims) {

        if (claims.size() != 3) {
            throw new RuntimeException("Quantidade de claims inválida");
        }

        String name = claims.get("Name", String.class);
        String role = claims.get("Role", String.class);
        String seed = claims.get("Seed", String.class);

        if (name == null || name.matches(".*\\d.*")) {
            throw new RuntimeException("Name inválido");
        }

        if (name.length() > 256) {
            throw new RuntimeException("Name muito grande");
        }

        if (Arrays.stream(Role.values()).map(Role::name).toList().contains(role)) {
            throw new RuntimeException("Role inválido");
        }

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