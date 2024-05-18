package com.budgetapp.config.jwtAuth;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;



@Service
@RequiredArgsConstructor
public class JwtTokenGenerator {

    private final JwtEncoder jwtEncoder;

    public String generateAccessToken(Authentication authentication) {

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("kanishka")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(30 , ChronoUnit.MINUTES))
                .subject(authentication.getName())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }


    public String generateRefreshToken(Authentication authentication) {

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("kanishka")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(45 , ChronoUnit.DAYS))
                .subject(authentication.getName())
                .claim("scope", "REFRESH_TOKEN")
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
