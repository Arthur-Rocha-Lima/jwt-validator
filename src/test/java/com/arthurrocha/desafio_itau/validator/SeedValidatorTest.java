package com.arthurrocha.desafio_itau.validator;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayNameGeneration(ReplaceUnderscores.class)
class SeedValidatorTest {

    @Mock
    private Claims claims;

    private SeedValidator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = new SeedValidator();
    }

    @Nested
    @DisplayName("Quando a seed for válida")
    class ValidSeed {

        @Test
        @DisplayName("deve aceitar número primo 2")
        void shouldAcceptPrimeTwo() {
            when(claims.get("Seed", String.class)).thenReturn("2");
            assertDoesNotThrow(() -> validator.validate(claims));
        }

        @Test
        @DisplayName("deve aceitar número primo 13")
        void shouldAcceptPrimeThirteen() {
            when(claims.get("Seed", String.class)).thenReturn("13");
            assertDoesNotThrow(() -> validator.validate(claims));
        }

        @Test
        @DisplayName("deve aceitar número primo grande 997")
        void shouldAcceptLargePrime() {
            when(claims.get("Seed", String.class)).thenReturn("997");
            assertDoesNotThrow(() -> validator.validate(claims));
        }
    }

    @Nested
    @DisplayName("Quando a seed for inválida")
    class InvalidSeed {

        @Test
        @DisplayName("deve lançar exceção quando seed é nula")
        void shouldThrowWhenSeedIsNull() {
            when(claims.get("Seed", String.class)).thenReturn(null);
            RuntimeException exception = assertThrows(RuntimeException.class, () -> validator.validate(claims));
            assertEquals("Seed is null or blank", exception.getMessage());
        }

        @Test
        @DisplayName("deve lançar exceção quando seed não é um número")
        void shouldThrowWhenSeedIsNotANumber() {
            when(claims.get("Seed", String.class)).thenReturn("abc");
            RuntimeException exception = assertThrows(RuntimeException.class, () -> validator.validate(claims));
            assertEquals("Seed is not a number", exception.getMessage());
        }

        @Test
        @DisplayName("deve lançar exceção quando seed é zero")
        void shouldThrowWhenSeedIsZero() {
            when(claims.get("Seed", String.class)).thenReturn("0");
            RuntimeException exception = assertThrows(RuntimeException.class, () -> validator.validate(claims));
            assertEquals("Seed is not a prime number", exception.getMessage());
        }

        @Test
        @DisplayName("deve lançar exceção quando seed é um")
        void shouldThrowWhenSeedIsOne() {
            when(claims.get("Seed", String.class)).thenReturn("1");
            RuntimeException exception = assertThrows(RuntimeException.class, () -> validator.validate(claims));
            assertEquals("Seed is not a prime number", exception.getMessage());
        }

        @Test
        @DisplayName("deve lançar exceção quando seed é número negativo")
        void shouldThrowWhenSeedIsNegative() {
            when(claims.get("Seed", String.class)).thenReturn("-7");
            RuntimeException exception = assertThrows(RuntimeException.class, () -> validator.validate(claims));
            assertEquals("Seed is not a prime number", exception.getMessage());
        }

        @Test
        @DisplayName("deve lançar exceção quando seed é número não primo")
        void shouldThrowWhenSeedIsNotPrime() {
            when(claims.get("Seed", String.class)).thenReturn("4");
            RuntimeException exception = assertThrows(RuntimeException.class, () -> validator.validate(claims));
            assertEquals("Seed is not a prime number", exception.getMessage());
        }
    }
}