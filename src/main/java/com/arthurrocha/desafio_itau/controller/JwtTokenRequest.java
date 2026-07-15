package com.arthurrocha.desafio_itau.controller;

/**
 * Record to hold the JWT token for validation request.
 */
public record JwtTokenRequest(String token) {
}