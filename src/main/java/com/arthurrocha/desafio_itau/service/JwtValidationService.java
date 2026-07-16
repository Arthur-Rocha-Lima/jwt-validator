package com.arthurrocha.desafio_itau.service;

import com.arthurrocha.desafio_itau.exception.InvalidJwtTokenException;
import com.arthurrocha.desafio_itau.utils.JwtUtils;
import com.arthurrocha.desafio_itau.validator.ClaimValidator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class JwtValidationService {

    private static final Logger logger = LogManager.getLogger(JwtValidationService.class);

    private final List<ClaimValidator> validators;

    public JwtValidationService(List<ClaimValidator> validators) {
        this.validators = validators;
    }

    public boolean validateToken(String token) {
        logger.debug("Iniciando validação do token [hash={}]", token);

        try {
            String unsignedToken = JwtUtils.getUnsignedToken(token);

            Jwt<Header, Claims> jwt = Jwts.parserBuilder()
                    .build()
                    .parseClaimsJwt(unsignedToken);

            Claims claims = jwt.getBody();

            validateClaims(claims);

            return true;
        } catch (InvalidJwtTokenException e) {
            logger.warn("Token inválido [token={}, motivo={}]", token, e.getReason());
            throw e;
        } catch (Exception e) {
            logger.error("Erro inesperado ao validar token [token={}]", token, e);
            throw new InvalidJwtTokenException("Estrutura do token inválida: " + e.getMessage());
        }
    }

    private void validateClaims(Claims claims) {
        if (claims.size() != 3) {
            throw new InvalidJwtTokenException("Quantidade de claims inválida. Esperado: 3, recebido: " + claims.size());
        }

        validators.forEach(v -> v.validate(claims));
    }
}