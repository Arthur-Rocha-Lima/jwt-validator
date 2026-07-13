package com.arthurrocha.desafio_itau.validator;

import com.arthurrocha.desafio_itau.enums.Role;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayNameGeneration(ReplaceUnderscores.class)
class RoleValidatorTest {

    @Mock
    private Claims claims;

    private RoleValidator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = new RoleValidator();
    }

    @Nested
    @DisplayName("Quando o role for válido")
    class ValidRole {

        @Test
        @DisplayName("deve aceitar role Admin")
        void shouldAcceptAdminRole() {
            when(claims.get("Role", String.class)).thenReturn("Admin");
            assertDoesNotThrow(() -> validator.validate(claims));
        }

        @Test
        @DisplayName("deve aceitar role Member")
        void shouldAcceptMemberRole() {
            when(claims.get("Role", String.class)).thenReturn("Member");
            assertDoesNotThrow(() -> validator.validate(claims));
        }

        @Test
        @DisplayName("deve aceitar role External")
        void shouldAcceptExternalRole() {
            when(claims.get("Role", String.class)).thenReturn("External");
            assertDoesNotThrow(() -> validator.validate(claims));
        }
    }

    @Nested
    @DisplayName("Quando o role for inválido")
    class InvalidRole {
        @Test
        @DisplayName("deve lançar exceção quando role é nulo")
        void shouldThrowWhenRoleIsNull() {
            when(claims.get("Role", String.class)).thenReturn(null);
            RuntimeException exception = assertThrows(RuntimeException.class, () -> validator.validate(claims));
            assertEquals("Role is null", exception.getMessage());
        }

        @Test
        @DisplayName("deve lançar exceção quando role está vazio")
        void shouldThrowWhenRoleIsEmpty() {
            when(claims.get("Role", String.class)).thenReturn("");
            RuntimeException exception = assertThrows(RuntimeException.class, () -> validator.validate(claims));
            assertEquals("Role does not exist", exception.getMessage());
        }

        @Test
        @DisplayName("deve lançar exceção quando role não existir no enum")
        void shouldThrowWhenRoleNotInEnum() {
            when(claims.get("Role", String.class)).thenReturn("invalid");
            RuntimeException exception = assertThrows(RuntimeException.class, () -> validator.validate(claims));
            assertEquals("Role does not exist", exception.getMessage());
        }

        @Test
        @DisplayName("deve lançar exceção quando role contém números")
        void shouldThrowWhenRoleContainsNumbers() {
            when(claims.get("Role", String.class)).thenReturn("ADMIN123");
            RuntimeException exception = assertThrows(RuntimeException.class, () -> validator.validate(claims));
            assertEquals("Role does not exist", exception.getMessage());
        }
    }
}