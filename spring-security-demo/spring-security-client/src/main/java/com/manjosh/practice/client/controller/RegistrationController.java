package com.manjosh.practice.client.controller;

import com.manjosh.practice.client.entity.CustomUser;
import com.manjosh.practice.client.entity.VerificationToken;
import com.manjosh.practice.client.event.RegistrationCompleteEvent;
import com.manjosh.practice.client.model.PasswordModel;
import com.manjosh.practice.client.model.UserModel;
import com.manjosh.practice.client.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
public class RegistrationController {

    private final UserService userService;

    private final ApplicationEventPublisher publisher;

    public RegistrationController(UserService userService, ApplicationEventPublisher publisher) {
        this.userService = userService;
        this.publisher = publisher;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserModel userModel, HttpServletRequest request) {
        CustomUser user = userService.registerUser(userModel);
        publisher.publishEvent(new RegistrationCompleteEvent(user, getApplicationUrl(request)));
        return ResponseEntity.ok(user);
    }


    @GetMapping("/api/hello")
    public String greeting() {
        return "Hello world";
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {
        String result = userService.validateVerificationToken(token);
        return result.equalsIgnoreCase("valid") ? "User verified successfully" : "invalid user";
    }

    @GetMapping("/resendVerificationToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest request) {
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        CustomUser user = verificationToken.getUser();
        resendVerificationTokenMail(getApplicationUrl(request), verificationToken.getToken());
        return "verification email sent";
    }


    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel, HttpServletRequest request) {
        CustomUser user = userService.findUserByEmail(passwordModel.getEmail());
        String url = "";
        if (user != null) {
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);
            url = passwordResetTokenMail(token, getApplicationUrl(request));
        }
        return url;
    }

    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token, @RequestBody PasswordModel passwordModel) {
        String result = userService.validatePasswordResetToken(token);
        if (!result.equalsIgnoreCase("valid")) {
            return result;
        }
        Optional<CustomUser> user = userService.getUserByPasswordResetToken(token);
        if (user.isPresent()) {
            userService.changePassword(user.get(), passwordModel.getNewPassword());
        } else return "invalid token";
        return "password reset successful";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody PasswordModel passwordModel, HttpServletRequest request){
        CustomUser user = userService.findUserByEmail(passwordModel.getEmail());
        if(!userService.verifyOldPassword(user,passwordModel.getOldPassword())){
            return "Invalid old password";
        }
        //save new password
        userService.changePassword(user,passwordModel.getNewPassword());
        return "password changed successfully";
    }

    private void resendVerificationTokenMail(String applicationUrl, String token) {
        String url = applicationUrl + "/verifyRegistration?token=" + token;
        //send verification email
        log.info("click link to verify your account  {}", url);
    }

    private String passwordResetTokenMail(String token, String applicationUrl) {
        String url = applicationUrl + "/savePassword?token=" + token;
        //send verification email
        log.info("click link to reset your password  {}", url);
        return url;
    }

    private String getApplicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
