package com.ro77en.blog_pessoal.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtService {

    @Value("${jwt.key}")
    private String jwtSecret;

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        long expiresAt = 3600L;

        UserAuthenticated user = (UserAuthenticated) authentication.getPrincipal();

        return JWT.create()
                .withIssuer("blog-pessoal")
                .withIssuedAt(now)
                .withSubject(authentication.getName())
                .withClaim("id", user.getId())
                .withExpiresAt(now.plusSeconds(expiresAt))
                .sign(Algorithm.HMAC256(jwtSecret));
    }
}
