package com.db.finki.www.build_board;

import com.db.finki.www.build_board.config.jwt.JWTUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class JWTUtilsTest {
    static JWTUtils jwtUtils;

    static {
        try {
            jwtUtils = new JWTUtils(new ObjectMapper());
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize JWTUtils", e);
        }
    }

    @Test
    void givenValidMinClaimSet_shouldGenerateValidToken(){

    }

    @Test
    void givenValidJWT_shouldReturnValidJWTAuthentication(){

    }
}
