package com.exponent_software.oauth2resourceserver.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;

@RestController
@RequestMapping(path = "/greet")
public class HomeController {

    @GetMapping(path = "/user")
    public String home(Authentication authentication) {
        return "Hello " + authentication.getName();
    }
    @GetMapping(path = "/admin")
    public String welcomeAdmin(Authentication authentication) {
        return "Hello " + authentication.getName();
    }
    @GetMapping(path = "/manager")
//    @PreAuthorize("hasRole('SCOPE_MANAGER')")
    public String welcomeManager(Authentication authentication) {
        return "Hello " + authentication.getName();
    }

}
