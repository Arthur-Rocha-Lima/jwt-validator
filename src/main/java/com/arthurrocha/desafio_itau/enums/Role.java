package com.arthurrocha.desafio_itau.enums;

public enum Role {
    ADMIN("Admin"),
    MEMBER("Member"),
    EXTERNAL("External");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
