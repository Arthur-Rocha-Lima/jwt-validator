package com.arthurrocha.desafio_itau.exception;

/**
 * Exception thrown when a JWT token fails validation according to the challenge rules.
 */
public class InvalidJwtTokenException extends RuntimeException {

    private final String reason;

    public InvalidJwtTokenException(String reason) {
        super(reason);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}