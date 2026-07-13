package com.arthurrocha.desafio_itau.validator;

import com.arthurrocha.desafio_itau.enums.Role;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class RoleValidator implements ClaimValidator {

    @Override
    public void validate(Claims claims) {
        String role = claims.get("Role", String.class);

        if (role == null) {
            throw new RuntimeException("Role is null");
        }

        if (Arrays.stream(Role.values())
                        .noneMatch(r -> r.getName().equals(role))) {
            throw new RuntimeException("Role does not exist");
        }
    }
}
