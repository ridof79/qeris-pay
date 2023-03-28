package com.enigma.qerispay.service;

import com.enigma.qerispay.auth.AuthRequestDTO;
import com.enigma.qerispay.auth.LoginDTO;
import com.enigma.qerispay.auth.UserDTO;

public interface AuthService {

    UserDTO registerCustomer(AuthRequestDTO user);

    UserDTO registerMerchant(AuthRequestDTO user);

    LoginDTO login(AuthRequestDTO user);

    void generateOTP(String phoneNumber);
}
