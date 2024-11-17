package com.exponent_software.oauth2resourceserver.controllers;

import com.exponent_software.oauth2resourceserver.entities.AppUser;
import com.exponent_software.oauth2resourceserver.service.SystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api")
public class SystemUsersController {

    private final SystemUserService systemUserService;

    public SystemUsersController(SystemUserService systemUserService) {
        this.systemUserService = systemUserService;
    }

    @PostMapping(path = "/user/login")
    public String login(Authentication authentication) {
        System.out.println(authentication.getName());
        UserDetails userDetails = systemUserService.loadUserByUsername(authentication.getName());
        return "successfully logged in";
    }

    @PostMapping(path = "/user/signup")
    public AppUser signupUser(@RequestBody AppUser appUser){
        return systemUserService.signUpAsUser(appUser);
    }

    @PostMapping(path = "/admin/signup")
    public AppUser adminSignup(@RequestBody AppUser appUser){
      return systemUserService.signUpAsAdmin(appUser);
    }

    @PostMapping(path = "/manager/signup")
    public AppUser managerSignup(@RequestBody AppUser appUser){
      return systemUserService.signUpAsManager(appUser);
    }

}
