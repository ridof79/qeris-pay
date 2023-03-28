package com.enigma.qerispay.service.impl;

import com.enigma.qerispay.auth.AuthRequestDTO;
import com.enigma.qerispay.auth.CustomUserDetails;
import com.enigma.qerispay.auth.LoginDTO;
import com.enigma.qerispay.auth.UserDTO;
import com.enigma.qerispay.dto.ERole;
import com.enigma.qerispay.entiy.Customer;
import com.enigma.qerispay.entiy.Merchant;
import com.enigma.qerispay.entiy.Role;
import com.enigma.qerispay.entiy.User;
import com.enigma.qerispay.repository.UserRepository;
import com.enigma.qerispay.security.JwtUtils;
import com.enigma.qerispay.service.AuthService;
import com.enigma.qerispay.service.RoleService;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

@Service
@Slf4j
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private JwtUtils jwtUtils;
    private RoleService roleService;

    @Override
    public UserDTO registerCustomer(AuthRequestDTO user) {
        Role role = roleService.getOrSave(ERole.valueOf(user.getRole()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userSaved = new Customer();
        return setRegister(user, role, userSaved);
    }

    private UserDTO setRegister(AuthRequestDTO user, Role role, User userSaved) {
        userSaved.setUsername(user.getUsername());
        userSaved.setPassword(user.getPassword());
        userSaved.setRoles(new ArrayList<>(Collections.singleton(role)));
        generateOTP(user.getPhoneNumber());
        User id = userRepository.save(userSaved);
        userSaved.setId(id.getId());
        return new UserDTO(userSaved);
    }

    @Override
    public UserDTO registerMerchant(AuthRequestDTO user) {
        Role role = roleService.getOrSave(ERole.valueOf(user.getRole()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userSaved = new Merchant();
        return setRegister(user, role, userSaved);
    }

    @Override
    public LoginDTO login(AuthRequestDTO user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtUtils.generateTokenFromUsername(userDetails.getUsername());
        return new LoginDTO(userDetails, token);
    }

    @Override
    public void generateOTP(String phoneNumber){
        Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("TWILIO_AUTH_TOKEN"));
        Verification verification = Verification.creator(
                        "VA8bc646fc3177cf74f222f89c6bc4d61a",
                        phoneNumber,
                        "whatsapp")
                .create();
        log.info("OTP has been successfully generated, and awaits your verification {}", LocalDateTime.now());
    }
}
