package com.enigma.qerispay.controller;

import com.enigma.qerispay.auth.AuthRequestDTO;
import com.enigma.qerispay.auth.LoginDTO;
import com.enigma.qerispay.auth.UserDTO;
import com.enigma.qerispay.dto.VerificationDTO;
import com.enigma.qerispay.service.AuthService;
import com.enigma.qerispay.service.UserService;
import com.enigma.qerispay.utils.customResponse.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private AuthService authService;
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequestDTO authRequest){
        UserDTO register = authService.registerCustomer(authRequest);
        Response<?> response = new Response<>("Successfuly create new Customer", register);

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
    @PostMapping("/register/merchant")
    public ResponseEntity<?> registerMerchant(@RequestBody AuthRequestDTO authRequest){
        UserDTO register = authService.registerMerchant(authRequest);
        Response<?> response = new Response<>("Successfuly create new Merchant", register);

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO authRequest) {
        LoginDTO loginDTO = authService.login(authRequest);
        Response<?> response = new Response<>("Successfully login", loginDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify")
    public String verifyUser(@RequestBody VerificationDTO verificationDTO) {
        if (userService.verify(verificationDTO)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }
}