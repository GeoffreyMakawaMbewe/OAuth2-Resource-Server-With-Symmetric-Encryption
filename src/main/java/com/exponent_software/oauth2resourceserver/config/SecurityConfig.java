package com.exponent_software.oauth2resourceserver.config;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;
import java.util.UUID;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    String uuid1 = UUID.randomUUID().toString();
    String uuid2 = UUID.randomUUID().toString();

    private final String resultantString = uuid1 + uuid2;
    @Value("${jwtSigningkey}")
    private String JWkey ;


//    @Bean
//    public UserDetailsService userDetailsService() {
//        System.out.println(JWkey);
//        var user1 = AppUser
//                .withUsername("Geoffrey")
//                .password("password")
//                .authorities("READ","WRITE","ROLE_USER")
//                .build();
//        var udm = new InMemoryUserDetailsManager();
//        udm.createUser(user1);
//
//        return udm;
//    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests( r -> r.requestMatchers("/api/user/signup").permitAll());
        http.authorizeHttpRequests( r -> r.requestMatchers("/api/admin/signup").permitAll());
        http.authorizeHttpRequests( r -> r.requestMatchers("/api/manager/signup").permitAll());
        http.authorizeHttpRequests( r -> r.requestMatchers("/api/user/login").authenticated());
        http.authorizeHttpRequests( r -> r.requestMatchers("/api/auth/getToken").hasAnyAuthority("USER","ADMIN", "MANAGER"));
//        http.authorizeHttpRequests( r -> r.anyRequest().hasAnyAuthority("SCOPE_USER", "SCOPE_WRITE"));
        http.authorizeHttpRequests( r -> r.requestMatchers("/greet/user").hasAuthority("SCOPE_USER"));
        http.authorizeHttpRequests( r -> r.requestMatchers("/greet/admin").hasAuthority("SCOPE_ADMIN"));
        http.authorizeHttpRequests( r -> r.requestMatchers("/greet/manager").hasAuthority("SCOPE_MANAGER"));

        http.httpBasic(Customizer.withDefaults());

        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

        return http
                .build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        JwtEncoder jwtEncoder= new NimbusJwtEncoder(new ImmutableSecret<>(JWkey.getBytes()));
        return jwtEncoder;
    }
    @Bean
    public JwtDecoder jwtDecoder(){

        byte[] keyBytes = JWkey.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes,0, keyBytes.length, "RSA");
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }
}
