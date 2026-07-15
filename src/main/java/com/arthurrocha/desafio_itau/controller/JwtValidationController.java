package com.arthurrocha.desafio_itau.controller;

import com.arthurrocha.desafio_itau.service.JwtValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
     * Validates a JWT token passed in the request body.
     *
     * @param jwtTokenRequest the request containing the JWT token string
     * @return true if the token is valid according to the challenge rules, false otherwise
     */
    @PostMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestBody JwtTokenRequest jwtTokenRequest) {
        boolean isValid = jwtValidationService.validateToken(jwtTokenRequest.token());
        return ResponseEntity.ok(isValid);
    }
}