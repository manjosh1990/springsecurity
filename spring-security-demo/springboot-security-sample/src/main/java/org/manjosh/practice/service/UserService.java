package org.manjosh.practice.service;

import lombok.RequiredArgsConstructor;
import org.manjosh.practice.entity.UserInfo;
import org.manjosh.practice.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserInfoRepository userInfoRepository;

    private final PasswordEncoder encoder;
    public String addUser(UserInfo user){
        user.setPassword(encoder.encode(user.getPassword()));
        userInfoRepository.save(user);
        return "user added to system";
    }
}
