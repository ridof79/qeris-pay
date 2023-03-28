package com.enigma.qerispay.controller;

import com.enigma.qerispay.auth.AuthRequestDTO;
import com.enigma.qerispay.auth.LoginDTO;
import com.enigma.qerispay.auth.UserDTO;
import com.enigma.qerispay.auth.VerificationDTO;
import com.enigma.qerispay.service.security.AuthService;
import com.enigma.qerispay.service.security.UserService;
import com.enigma.qerispay.utils.constant.ApiUrlConstant;
import com.enigma.qerispay.utils.constant.ControllerConstant;
import com.enigma.qerispay.utils.customResponse.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiUrlConstant.AUTH_PATH)
@AllArgsConstructor
public class AuthController {

    private AuthService authService;
    private UserService userService;

    @PostMapping("/register/customer")
    public ResponseEntity<?> registerCustomer(@RequestBody AuthRequestDTO authRequest){
        UserDTO register = authService.registerCustomer(authRequest);
        Response<?> response = new Response<>(ControllerConstant.SUCCESSFULLY_CREATE_CUSTOMER, register);

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PostMapping("/register/merchant")
    public ResponseEntity<?> registerMerchant(@RequestBody AuthRequestDTO authRequest){
        UserDTO register = authService.registerMerchant(authRequest);
        Response<?> response = new Response<>(ControllerConstant.SUCCESSFULLY_CREATE_MERCHANT, register);

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO authRequest) {
        LoginDTO loginDTO = authService.login(authRequest);
        Response<?> response = new Response<>(ControllerConstant.SUCCESSFULLY_LOGIN, loginDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody VerificationDTO verificationDTO) {
        if (userService.verify(verificationDTO)) {
            return new ResponseEntity<>(ControllerConstant.VERIFICATION_SUCCESS, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ControllerConstant.VERIFICATION_FAILED, HttpStatus.BAD_REQUEST);
        }
    }
}