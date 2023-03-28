package com.enigma.qerispay.service.impl.security;

import com.enigma.qerispay.dto.ERole;
import com.enigma.qerispay.entiy.Role;
import com.enigma.qerispay.repository.RoleRepository;
import com.enigma.qerispay.service.security.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getOrSave(ERole role) {
        return roleRepository.findByRole(role)
                .orElseGet(() -> roleRepository.save(new Role(null, role)));
    }
}
