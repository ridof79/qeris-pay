package com.enigma.qerispay.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VerificationDTO {
    private String userId;
    private String phoneNumber;
    private String code;
}
