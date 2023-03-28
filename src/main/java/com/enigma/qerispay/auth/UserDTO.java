package com.enigma.qerispay.auth;

import com.enigma.qerispay.entiy.Role;
import com.enigma.qerispay.entiy.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserDTO {
    private String username;
    private String id;
    private List<String> roles;

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.roles = user.getRoles().stream().map(role -> role.getRole().name()).collect(Collectors.toList());
        this.id = user.getId();
    }
}