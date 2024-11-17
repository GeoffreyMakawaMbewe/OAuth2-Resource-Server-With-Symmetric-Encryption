package com.exponent_software.oauth2resourceserver.repositories;

import com.exponent_software.oauth2resourceserver.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SystemUsersRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);
}
