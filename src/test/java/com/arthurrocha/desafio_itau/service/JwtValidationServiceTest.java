package com.arthurrocha.desafio_itau.service;

import com.arthurrocha.desafio_itau.exception.InvalidJwtTokenException;
import com.arthurrocha.desafio_itau.validator.ClaimValidator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Base64;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@DisplayNameGeneration(ReplaceUnderscores.class)
class JwtValidationServiceTest {

    @Mock
    private ClaimValidator validator;

    private JwtValidationService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new JwtValidationService(List.of(validator));
    }

    @Nested
    @DisplayName("Quando o token for válido")
    class ValidToken {

        @Test
        @DisplayName("deve validar token corretamente")
        void shouldValidateTokenSuccessfully() {
            String token = generateTokenWith3Claims();

            assertDoesNotThrow(() -> {
                boolean result = service.validateToken(token);
                assertTrue(result);
            });

            verify(validator, times(1)).validate(any(Claims.class));
        }
    }

    @Nested
    @DisplayName("Quando o token for inválido")
    class InvalidToken {

        @Test
        @DisplayName("deve lançar exceção quando token é mal formatado")
        void shouldThrowExceptionWhenTokenMalformed() {
            String invalidToken = "token.invalido";

            assertThrows(InvalidJwtTokenException.class, () -> {
                service.validateToken(invalidToken);
            });

            verify(validator, never()).validate(any());
        }

        @Test
        @DisplayName("deve lançar exceção quando quantidade de claims é inválida")
        void shouldThrowExceptionWhenClaimsSizeInvalid() {
            String token = generateTokenWith1Claim();

            assertThrows(InvalidJwtTokenException.class, () -> {
                service.validateToken(token);
            });
        }

        @Test
        @DisplayName("deve lançar exceção quando validator lança exceção")
        void shouldThrowExceptionWhenValidatorThrowsException() {
            String token = generateTokenWith3Claims();

            doThrow(new RuntimeException("erro")).when(validator).validate(any());

            assertThrows(InvalidJwtTokenException.class, () -> {
                service.validateToken(token);
            });
        }
    }

    // =======================
    // Métodos auxiliares
    // =======================

    private String generateTokenWith3Claims() {
        Claims claims = new DefaultClaims();
        claims.put("Role", "Admin");
        claims.put("Seed", "2");
        claims.put("Name", "Arthur");

        return buildToken(claims);
    }

    private String generateTokenWith1Claim() {
        Claims claims = new DefaultClaims();
        claims.put("Name", "Arthur");

        return buildToken(claims);
    }

    private String buildToken(Claims claims) {
        String headerJson = "{\"alg\":\"HS256\"}";
        String payloadJson = "{";

        boolean first = true;
        for (String key : claims.keySet()) {
            if (!first) payloadJson += ",";
            payloadJson += "\"" + key + "\":\"" + claims.get(key) + "\"";
            first = false;
        }
        payloadJson += "}";

        String header = Base64.getUrlEncoder().withoutPadding().encodeToString(headerJson.getBytes());
        String body = Base64.getUrlEncoder().withoutPadding().encodeToString(payloadJson.getBytes());

        return header + "." + body + "." + "XXXX";
    }
}