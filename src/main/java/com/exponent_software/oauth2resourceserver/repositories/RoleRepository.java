package com.exponent_software.oauth2resourceserver.repositories;

import com.exponent_software.oauth2resourceserver.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {


    Optional<Role> findByName(String name);
}
