package com.example.service.impl;

import com.example.entity.User;
import com.example.entity.common.UserPrinciple;
import com.example.repository.UserRepository;
import com.example.service.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class SecurityServiceImpl implements SecurityService {
    private final UserRepository userRepository;

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // we need to get out own user from database
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("This user does not exist"));
        //return user information as an UserDetail
        return new UserPrinciple(user);
    }
}
