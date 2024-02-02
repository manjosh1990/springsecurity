package com.manjosh.practice.client.service;

import com.manjosh.practice.client.entity.CustomUser;
import com.manjosh.practice.client.entity.VerificationToken;
import com.manjosh.practice.client.model.UserModel;

import java.util.Optional;

public interface UserService {

    CustomUser registerUser(UserModel userModel);

    void saveVerficationTokenForUser(CustomUser user, String token);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);

    CustomUser findUserByEmail(String email);

    void createPasswordResetTokenForUser(CustomUser user, String token);

    String validatePasswordResetToken(String token);

    Optional<CustomUser> getUserByPasswordResetToken(String token);

    void changePassword(CustomUser customUser, String newPassword);

    boolean verifyOldPassword(CustomUser user, String oldPassword);
}
