package com.arthurrocha.desafio_itau.controller;

import jakarta.validation.constraints.NotBlank;

/**
 * Record to hold the JWT token for validation request.
 */
public record JwtTokenRequest(
        @NotBlank(message = "Token cannot be blank")
        String token
) {
}