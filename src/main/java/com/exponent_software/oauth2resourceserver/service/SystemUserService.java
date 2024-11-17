package com.exponent_software.oauth2resourceserver.service;

import com.exponent_software.oauth2resourceserver.entities.AppUser;
import com.exponent_software.oauth2resourceserver.entities.Role;
import com.exponent_software.oauth2resourceserver.repositories.SystemUsersRepository;
import com.exponent_software.oauth2resourceserver.util.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class SystemUserService implements UserDetailsService {

    private final SystemUsersRepository systemUsersRepository;
    private final PasswordEncoder passwordEncoder;

    public SystemUserService(SystemUsersRepository systemUsersRepository, PasswordEncoder passwordEncoder) {
        this.systemUsersRepository = systemUsersRepository;
        this.passwordEncoder = passwordEncoder;
    }

        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            //TODO
            System.out.println("Loading user by email " + email);
            Optional<AppUser> systemUserOptional = systemUsersRepository.findByEmail(email);

            if (systemUserOptional.isPresent()) {
                AppUser appUser = systemUserOptional.get();
                System.out.println(" Retrieved user " + appUser);
                return new SecurityUser(appUser);
            } else {
                throw new UsernameNotFoundException("AppUser with email" + email + " not found");
            }
        }
        public AppUser signUpAsUser(AppUser appUser){
           Optional<AppUser> userAlreadyExists = systemUsersRepository.findByEmail(appUser.getEmail());

           if (userAlreadyExists.isPresent()) {
               throw new RuntimeException("AppUser with email " + appUser.getEmail() + " already exists");
           }
           appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

            Role userRole  = Role
                    .builder()
                    .name(com.exponent_software.oauth2resourceserver.util.Role.USER.name())
                    .build();

           appUser.getRoles().add(userRole);
            return systemUsersRepository.save(appUser);
        }

}
