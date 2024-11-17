package com.exponent_software.oauth2resourceserver.controllers;

import com.exponent_software.oauth2resourceserver.service.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthenticationController {

    private final TokenService tokenService;

    public AuthenticationController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping(path = "/getToken")
    public String getToken(Authentication authentication) {
        return tokenService.generateToken(authentication);
    }

}
