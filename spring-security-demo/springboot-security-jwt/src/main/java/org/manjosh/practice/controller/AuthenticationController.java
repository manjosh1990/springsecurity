package org.manjosh.practice.controller;

import lombok.RequiredArgsConstructor;
import org.manjosh.practice.dto.AuthRequestDto;
import org.manjosh.practice.entity.JwtResponseDto;
import org.manjosh.practice.entity.RefreshToken;
import org.manjosh.practice.entity.RefreshTokenRequest;
import org.manjosh.practice.entity.UserInfo;
import org.manjosh.practice.service.JwtService;
import org.manjosh.practice.service.RefreshTokenService;
import org.manjosh.practice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    @PostMapping("/register")
    public String addNewUser(@RequestBody UserInfo userInfo){
        return userService.addUser(userInfo);
    }

    @PostMapping("/authenticate")
    public JwtResponseDto authenticateAndGetToken(@RequestBody AuthRequestDto authRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if(authentication.isAuthenticated()){
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getUsername());
            return JwtResponseDto.builder().
                    accessToken(jwtService.generateToken(authRequest.getUsername()))
                    .token(refreshToken.getToken()).build();
        }else
            throw new UsernameNotFoundException("invalid user request");
    }

    @PostMapping("/refresh")
    public JwtResponseDto getRefreshToken(@RequestBody RefreshTokenRequest request){
            return refreshTokenService.findByToken(request.getToken())
                    .map(refreshTokenService::verifyExpiration)
                    .map(RefreshToken::getUserInfo)
                    .map(userInfo -> {
                        String accessToken = jwtService.generateToken(userInfo.getUsername());
                        return JwtResponseDto.builder()
                                .accessToken(accessToken)
                                .token(request.getToken()).build();
                    })
                    .orElseThrow(()->new RuntimeException("Invalid refresh token!")
                    );

    }
}
