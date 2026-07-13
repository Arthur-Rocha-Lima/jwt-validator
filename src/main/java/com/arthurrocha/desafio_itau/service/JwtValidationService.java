package com.arthurrocha.desafio_itau.service;

import com.arthurrocha.desafio_itau.enums.Role;
import com.arthurrocha.desafio_itau.utils.JwtUtils;
import com.arthurrocha.desafio_itau.validator.ClaimValidator;
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

    private final List<ClaimValidator> validators;

    public JwtValidationService(List<ClaimValidator> validators) {
        this.validators = validators;
    }

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

        validators.forEach(v -> v.validate(claims));
    }
}