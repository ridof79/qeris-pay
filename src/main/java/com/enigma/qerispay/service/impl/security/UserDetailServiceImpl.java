package com.enigma.qerispay.service.impl.security;

import com.enigma.qerispay.auth.CustomUserDetails;
import com.enigma.qerispay.entiy.User;
import com.enigma.qerispay.repository.UserRepository;
import com.enigma.qerispay.utils.constant.NotFoundConstant;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> appUser = userRepository.findByUsername(username);
        if ((!appUser.isPresent())) {
            try {
                throw new Exception(NotFoundConstant.USER_NOT_FOUND_CONS);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return new CustomUserDetails(appUser.get());
    }
}