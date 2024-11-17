package com.exponent_software.oauth2resourceserver;

import com.exponent_software.oauth2resourceserver.entities.Role;
import com.exponent_software.oauth2resourceserver.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class OAuth2ResourceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuth2ResourceServerApplication.class, args);
    }

//    @Bean
    public CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
        return args -> {
            System.out.println("Hello World");

            Role role1 = Role
                    .builder()
                    .name(com.exponent_software.oauth2resourceserver.util.Role.USER.name())
                    .build();

            Role role2 = Role
                    .builder()
                    .name(com.exponent_software.oauth2resourceserver.util.Role.ADMIN.name())
                    .build();
            Role role3 = Role
                    .builder()
                    .name(com.exponent_software.oauth2resourceserver.util.Role.MANAGER.name())
                    .build();
           roleRepository.saveAll(List.of(role1, role2, role3));
        };
    }

}
