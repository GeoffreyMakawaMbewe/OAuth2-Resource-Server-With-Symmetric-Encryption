package com.exponent_software.oauth2resourceserver.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenService {

    private final JwtEncoder jwtEncoder;

    public TokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }
    public String generateToken(Authentication authentication){

        Instant instant = Instant.now();

        List<String> scopes = authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority -> !authority.startsWith("ROLE"))
                .collect(Collectors.toList());

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet
                .builder()
                .issuer("Exponent Technologies Inc API")
                .subject(authentication.getName())
                .issuedAt(instant)
                .expiresAt(instant.plus(3, ChronoUnit.HOURS))
                .claim("scope", scopes)
                .build();
        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters
                .from(JwsHeader.with(MacAlgorithm.HS512).build(),  jwtClaimsSet );

        return jwtEncoder
                .encode(jwtEncoderParameters).getTokenValue();
    }
}
