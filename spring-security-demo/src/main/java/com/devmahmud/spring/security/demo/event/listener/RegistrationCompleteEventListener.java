package com.devmahmud.spring.security.demo.event.listener;

import com.devmahmud.spring.security.demo.entity.User;
import com.devmahmud.spring.security.demo.event.RegistrationCompleteEvent;
import com.devmahmud.spring.security.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class RegistrationCompleteEventListener
        implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //Create the verification token for the user with link
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token,user);
//        Send mail to user
        String url =
                event.getApplicationUrl() + "verifyRegistration?token="+token;

        //send verification email from here
        log.info("Click the link to verify your account: {}",
                url);

    }
}
