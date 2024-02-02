package com.manjosh.practice.oauthauthserver.service;

import com.manjosh.practice.oauthauthserver.entity.CustomUser;
import com.manjosh.practice.oauthauthserver.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CustomUser user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("No User Found");
        }
        return new User(user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                getAutorities(List.of(user.getRole())));
    }

    private Collection<? extends GrantedAuthority> getAutorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
