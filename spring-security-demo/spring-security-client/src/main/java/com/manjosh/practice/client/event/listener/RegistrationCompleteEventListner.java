package com.manjosh.practice.client.event.listener;

import com.manjosh.practice.client.entity.CustomUser;
import com.manjosh.practice.client.event.RegistrationCompleteEvent;
import com.manjosh.practice.client.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
@Slf4j
public class RegistrationCompleteEventListner implements ApplicationListener<RegistrationCompleteEvent> {
    private UserService userService;

    public RegistrationCompleteEventListner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //create verification token with link

        CustomUser user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerficationTokenForUser(user,token);

        //send mail to user with url

        String url = event.getApplicationUrl()+"/verifyRegistration?token="+token;
        //send verification email
        log.info("click link to verify your account  {}",url);
    }
}
