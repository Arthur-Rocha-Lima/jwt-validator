package com.arthurrocha.desafio_itau.controller;

import com.arthurrocha.desafio_itau.service.JwtValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jwt")
public class JwtValidationController {

    private final JwtValidationService jwtValidationService;

    @Autowired
    public JwtValidationController(JwtValidationService jwtValidationService) {
        this.jwtValidationService = jwtValidationService;
    }

    /**
     * Validates a JWT token passed as a query parameter.
     *
     * @param token the JWT token string
     * @return true if the token is valid according to the challenge rules, false otherwise
     */
    @GetMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestParam String token) {
        boolean isValid = jwtValidationService.validateToken(token);
        return ResponseEntity.ok(isValid);
    }
}