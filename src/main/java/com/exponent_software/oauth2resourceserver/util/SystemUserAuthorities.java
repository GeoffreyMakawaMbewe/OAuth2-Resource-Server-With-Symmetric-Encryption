package com.exponent_software.oauth2resourceserver.util;

import org.springframework.security.core.GrantedAuthority;

public class SystemUserAuthorities implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return "";
    }
}
