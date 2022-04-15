package com.devmahmud.spring.security.demo.controller;

import com.devmahmud.spring.security.demo.entity.User;
import com.devmahmud.spring.security.demo.entity.VerificationToken;
import com.devmahmud.spring.security.demo.event.RegistrationCompleteEvent;
import com.devmahmud.spring.security.demo.model.UserModel;
import com.devmahmud.spring.security.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
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

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token){
        String result = userService.validateVerificationToken(token);

        if(result.equalsIgnoreCase("valid")){
            return "User verified!";
        }else{
            return "Bad User";
        }
    }

    @GetMapping("/resendVerifyToken")
    public String resendVerificationToken(@RequestParam("token")String oldToken, HttpServletRequest servletRequest){
        VerificationToken verificationToken =
                userService.generateNewVerificationToken(oldToken);
        User user = verificationToken.getUser();
        resendVerificationTokenMail(user, verificationToken,applicationUrl(servletRequest));

        return "Verification Link sent to you mail!";
    }

    private void resendVerificationTokenMail(User user, VerificationToken verificationToken, String applicationUrl) {
//        Send mail to user
        String url =
                applicationUrl + "/verifyRegistration?token="+verificationToken.getToken();

        //send verification email from here
        log.info("Click the link to verify your account: {}",
                url);
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
