package com.manjosh.practice.client.service;

import com.manjosh.practice.client.entity.CustomUser;
import com.manjosh.practice.client.entity.PasswordResetToken;
import com.manjosh.practice.client.entity.VerificationToken;
import com.manjosh.practice.client.model.UserModel;
import com.manjosh.practice.client.repository.PasswordResetTokenRepository;
import com.manjosh.practice.client.repository.UserRepository;
import com.manjosh.practice.client.repository.VerificationTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final VerificationTokenRepository verificationTokenRepository;

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, VerificationTokenRepository verificationTokenRepository, PasswordResetTokenRepository passwordResetTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenRepository = verificationTokenRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }



    @Override
    public CustomUser registerUser(UserModel userModel) {
        CustomUser user = new CustomUser();
        user.setEmail(userModel.getEmail());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        user.setRole("USER_ROLE");
        return userRepository.save(user);
    }

    @Override
    public void saveVerficationTokenForUser(CustomUser user, String token) {
        VerificationToken verificationToken = new VerificationToken(user,token);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if(verificationToken == null) return "invalid token";
        CustomUser user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if(verificationToken.getExpirationTime().getTime() - cal.getTime().getTime() <=0){
            verificationTokenRepository.delete(verificationToken);
            return "expired";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    @Override
    public CustomUser findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void createPasswordResetTokenForUser(CustomUser user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(user,token);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if(passwordResetToken == null) return "invalid token";
        Calendar cal = Calendar.getInstance();
        if(passwordResetToken.getExpirationTime().getTime() - cal.getTime().getTime() <=0){
            passwordResetTokenRepository.delete(passwordResetToken);
            return "expired";
        }
        return "valid";
    }

    @Override
    public Optional<CustomUser> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
    }

    @Override
    public void changePassword(CustomUser user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public boolean verifyOldPassword(CustomUser user, String oldPassword) {
        if(user == null) return false;
        return passwordEncoder.matches(oldPassword,user.getPassword());
    }
}
