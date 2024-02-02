package org.manjosh.practice.config;

import lombok.RequiredArgsConstructor;
import org.manjosh.practice.entity.UserInfo;
import org.manjosh.practice.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;


public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserInfoRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfoOptional = repository.findByUsername(username);
       return userInfoOptional.map(UserInfoUserDetails::new)
                .orElseThrow(()->new UsernameNotFoundException("User not found " + username));
    }
}
