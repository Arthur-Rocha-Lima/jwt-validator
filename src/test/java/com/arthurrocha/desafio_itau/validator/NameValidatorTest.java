package com.arthurrocha.desafio_itau.validator;

import com.arthurrocha.desafio_itau.exception.InvalidJwtTokenException;
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
class NameValidatorTest {

    @Mock
    private Claims claims;

    private NameValidator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = new NameValidator();
    }

    @Nested
    @DisplayName("Quando o nome for válido")
    class ValidName {

        @Test
        @DisplayName("deve aceitar nome contendo apenas letras")
        void shouldAcceptNameWithOnlyLetters() {
            when(claims.get("Name", String.class)).thenReturn("JoaoSilva");
            assertDoesNotThrow(() -> validator.validate(claims));
        }

        @Test
        @DisplayName("deve aceitar nome com espaços")
        void shouldAcceptNameWithSpaces() {
            when(claims.get("Name", String.class)).thenReturn("Joao Silva");
            assertDoesNotThrow(() -> validator.validate(claims));
        }

        @Test
        @DisplayName("deve aceitar nome com letras minúsculas e maiúsculas")
        void shouldAcceptNameWithMixedCase() {
            when(claims.get("Name", String.class)).thenReturn("Joao Silva");
            assertDoesNotThrow(() -> validator.validate(claims));
        }

        @Test
        @DisplayName("deve aceitar nome vazio")
        void shouldAcceptEmptyName() {
            when(claims.get("Name", String.class)).thenReturn("");
            assertDoesNotThrow(() -> validator.validate(claims));
        }

        @Test
        @DisplayName("deve aceitar nome com caracteres especiais (sem dígitos)")
        void shouldAcceptSpecialCharactersNoDigits() {
            when(claims.get("Name", String.class)).thenReturn("Jo@o!");
            assertDoesNotThrow(() -> validator.validate(claims));
        }
    }

    @Nested
    @DisplayName("Quando o nome for inválido")
    class InvalidName {

        @Test
        @DisplayName("deve lançar exceção quando nome é nulo")
        void shouldThrowWhenNameIsNull() {
            when(claims.get("Name", String.class)).thenReturn(null);
            InvalidJwtTokenException exception = assertThrows(InvalidJwtTokenException.class, () -> validator.validate(claims));
            assertEquals("Name is null", exception.getMessage());
        }

        @Test
        @DisplayName("deve lançar exceção quando nome contém dígitos")
        void shouldThrowWhenNameContainsDigits() {
            when(claims.get("Name", String.class)).thenReturn("Joao123");
            InvalidJwtTokenException exception = assertThrows(InvalidJwtTokenException.class, () -> validator.validate(claims));
            assertEquals("Name has numbers", exception.getMessage());
        }

        @Test
        @DisplayName("deve lançar exceção quando nome excede 256 caracteres")
        void shouldThrowWhenNameTooLong() {
            String tooLong = "a".repeat(257);
            when(claims.get("Name", String.class)).thenReturn(tooLong);
            InvalidJwtTokenException exception = assertThrows(InvalidJwtTokenException.class, () -> validator.validate(claims));
            assertEquals("Name is too big", exception.getMessage());
        }
    }
}