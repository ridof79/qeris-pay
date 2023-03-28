package com.enigma.qerispay.service.impl.security;

import com.enigma.qerispay.auth.VerificationDTO;
import com.enigma.qerispay.entiy.User;
import com.enigma.qerispay.repository.UserRepository;
import com.enigma.qerispay.service.security.UserService;
import com.enigma.qerispay.utils.constant.NotFoundConstant;
import com.enigma.qerispay.utils.exception.DataNotFoundException;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User Update(User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            return userRepository.save(user);
        } else {
            throw new DataNotFoundException(String.format(NotFoundConstant.USER_NOT_FOUND, user.getId()));
        }
    }

    @Override
    public User getUserById(String id) {
        if (userRepository.findById(id).isPresent()) {
            return userRepository.findById(id).get();
        } else {
            throw new DataNotFoundException(String.format(NotFoundConstant.USER_NOT_FOUND, id));
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(String id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new DataNotFoundException(String.format(NotFoundConstant.USER_NOT_FOUND, id));
        }
    }

    @Override
    public boolean verify(VerificationDTO verificationDTO) {
        Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("TWILIO_AUTH_TOKEN"));
            try {
                VerificationCheck verificationCheck = VerificationCheck.creator(
                                "VA8bc646fc3177cf74f222f89c6bc4d61a")
                        .setTo(verificationDTO.getPhoneNumber())
                        .setCode(verificationDTO.getCode())
                        .create();
            } catch (Exception e) {
                return false;
            }
            if (!userRepository.findById(verificationDTO.getUserId()).isPresent()) return false;
            User user = userRepository.findById(verificationDTO.getUserId()).get();
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        }
}

