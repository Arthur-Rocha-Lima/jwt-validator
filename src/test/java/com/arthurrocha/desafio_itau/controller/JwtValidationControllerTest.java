package com.arthurrocha.desafio_itau.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class JwtValidationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String INVALID_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJSb2xlIjoiTm9uZSIsIlNlZWQiOiIyIiwiTmFtZSI6Ik9uaGlubyBBcmF1am8ifQ.IC2rEE_E8w55h_iRV08NTMvpNQU6oDbfBL1BLbD1jw8";
    private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJSb2xlIjoiQWRtaW4iLCJTZWVkIjoiNzg0MSIsIk5hbWUiOiJPbmhpbm8gQXJhdWpvIn0.v-85wtz0CBTh_6Jje7jwE-OPEagZ7iZB79A1jCkyPdE";

    @Test
    @DisplayName("Deve retornar false para token inválido")
    void shouldReturnFalseForInvalidToken() throws Exception {
        mockMvc.perform(get("/jwt/validate")
                        .param("token", INVALID_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    @DisplayName("Deve retornar true para token válido")
    void shouldReturnTrueForValidToken() throws Exception {
        mockMvc.perform(get("/jwt/validate")
                        .param("token", VALID_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}