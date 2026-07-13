package com.arthurrocha.desafio_itau.validator;

import io.jsonwebtoken.Claims;

public interface ClaimValidator {
    void validate(Claims claims);
}
