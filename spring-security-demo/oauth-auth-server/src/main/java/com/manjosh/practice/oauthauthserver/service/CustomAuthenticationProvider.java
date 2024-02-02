package com.manjosh.practice.oauthauthserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailsService customUserDetailsService;


    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
        return checkPassword(userDetails,password);
    }

    private Authentication checkPassword(UserDetails userDetails, String password) {
        if(passwordEncoder.matches(password,userDetails.getPassword())){
            return new UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
        }else {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
