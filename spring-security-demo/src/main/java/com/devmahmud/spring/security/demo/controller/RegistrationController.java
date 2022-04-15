package com.devmahmud.spring.security.demo.controller;

import com.devmahmud.spring.security.demo.entity.User;
import com.devmahmud.spring.security.demo.event.RegistrationCompleteEvent;
import com.devmahmud.spring.security.demo.model.UserModel;
import com.devmahmud.spring.security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RegistrationController {

    @Value("${server.port}")
    String port;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel,
                               final HttpServletRequest request) {
        User user = userService.registerUser(userModel);
        applicationEventPublisher.publishEvent(
                new RegistrationCompleteEvent(user,
                        applicationUrl(request)));
        return "Success!";
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }

    @GetMapping("/")
    public String test() {
        return "Service UP and running on Port: " + port;
    }
}
