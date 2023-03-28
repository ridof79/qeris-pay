package com.enigma.qerispay.service;

import com.enigma.qerispay.dto.VerificationDTO;
import com.enigma.qerispay.entiy.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User Update(User user);
    User getUserById(String id);
    List<User> getAllUsers();
    void deleteUser(String userId);
    boolean verify(VerificationDTO verificationDTO);
}
