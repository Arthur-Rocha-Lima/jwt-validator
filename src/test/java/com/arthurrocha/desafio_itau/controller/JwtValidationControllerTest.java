package com.arthurrocha.desafio_itau.controller;

import com.arthurrocha.desafio_itau.exception.InvalidJwtTokenException;
import com.arthurrocha.desafio_itau.service.JwtValidationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class JwtValidationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtValidationService jwtValidationService;

    @Test
    @DisplayName("Deve retornar 422 para token inválido")
    void shouldReturn422ForInvalidToken() throws Exception {
        // Given
        String token = "invalid-token";
        given(jwtValidationService.validateToken(token))
                .willThrow(new InvalidJwtTokenException("Token malformed"));

        // When & Then
        mockMvc.perform(post("/jwt/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"token\":\"" + token + "\"}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.status").value(422))
                .andExpect(jsonPath("$.message").value("Token inválido: Token malformed"))
                .andExpect(jsonPath("$.error").value("Unprocessable Entity"));

        // Then verify the service was called with the token
        verify(jwtValidationService).validateToken(token);
    }

    @Test
    @DisplayName("Deve retornar 200 com true para token válido")
    void shouldReturn200ForValidToken() throws Exception {
        // Given
        String token = "valid-token";
        given(jwtValidationService.validateToken(token)).willReturn(true);

        // When & Then
        mockMvc.perform(post("/jwt/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"token\":\"" + token + "\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        // Then verify the service was called with the token
        verify(jwtValidationService).validateToken(token);
    }
}