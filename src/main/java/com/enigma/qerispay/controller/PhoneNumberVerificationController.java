package com.enigma.qerispay.controller;

import com.enigma.qerispay.dto.VerificationDTO;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static com.twilio.example.ValidationExample.ACCOUNT_SID;
import static com.twilio.example.ValidationExample.AUTH_TOKEN;

@RestController
@RequestMapping(path = "api/phoneNumber")
@Slf4j
public class PhoneNumberVerificationController {

    @GetMapping(value = "/generateOTP")
    public ResponseEntity<String> generateOTP(@RequestBody VerificationDTO numberVerificationDTO){

        Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("TWILIO_AUTH_TOKEN"));

        Verification verification = Verification.creator(
                        "VA8bc646fc3177cf74f222f89c6bc4d61a",
                        numberVerificationDTO.getPhoneNumber(),
                        "whatsapp") // this is your channel type
                .create();

        System.out.println(verification.getStatus());

        log.info("OTP has been successfully generated, and awaits your verification {}", LocalDateTime.now());

        return new ResponseEntity<>("Your OTP has been sent to your verified phone number", HttpStatus.OK);
    }

    @GetMapping("/verifyOTP")
    public ResponseEntity<?> verifyUserOTP(@RequestBody VerificationDTO numberVerificationDTO) throws Exception {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        try {
            VerificationCheck verificationCheck = VerificationCheck.creator(
                            "VA8bc646fc3177cf74f222f89c6bc4d61a")
                    .setTo(numberVerificationDTO.getPhoneNumber())
                    .setCode(numberVerificationDTO.getCode())
                    .create();

            System.out.println(verificationCheck.getStatus());

        } catch (Exception e) {
            return new ResponseEntity<>("Verification failed.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("This user's verification has been completed successfully", HttpStatus.OK);
    }

}
