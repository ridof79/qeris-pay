package com.enigma.qerispay.repository;

import com.enigma.qerispay.dto.ERole;
import com.enigma.qerispay.entiy.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRole(ERole role);
}
