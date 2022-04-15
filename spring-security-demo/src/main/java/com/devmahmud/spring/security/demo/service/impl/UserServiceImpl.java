package com.devmahmud.spring.security.demo.service.impl;

import com.devmahmud.spring.security.demo.entity.User;
import com.devmahmud.spring.security.demo.entity.VerificationToken;
import com.devmahmud.spring.security.demo.model.UserModel;
import com.devmahmud.spring.security.demo.repository.UserRepository;
import com.devmahmud.spring.security.demo.repository.VerificationTokenRepository;
import com.devmahmud.spring.security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Override
    public User registerUser(UserModel userModel) {
        User user = new User();
        user.setEmail(userModel.getEmail());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    public void saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(token, user);
        verificationTokenRepository.save(verificationToken);
    }
}
