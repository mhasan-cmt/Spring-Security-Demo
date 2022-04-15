package com.devmahmud.spring.security.demo.service;

import com.devmahmud.spring.security.demo.entity.User;
import com.devmahmud.spring.security.demo.entity.VerificationToken;
import com.devmahmud.spring.security.demo.model.UserModel;

public interface UserService {
    User registerUser(UserModel userModel);

    void saveVerificationTokenForUser(String token, User user);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String token);
}
