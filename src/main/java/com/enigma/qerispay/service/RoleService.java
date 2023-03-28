package com.enigma.qerispay.service;

import com.enigma.qerispay.dto.ERole;
import com.enigma.qerispay.entiy.Role;

public interface RoleService {
    Role getOrSave(ERole role);
}
