package com.arthurrocha.desafio_itau.utils;

public class JwtUtils {

    public static String getUnsignedToken(String token) throws Exception {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new Exception("Token sem estrutura correta!");
        }

        return parts[0] + "." + parts[1] + ".";
    }
}
