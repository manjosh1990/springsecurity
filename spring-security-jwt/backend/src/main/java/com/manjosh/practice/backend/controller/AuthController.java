package com.manjosh.practice.backend.controller;

import com.manjosh.practice.backend.config.UserAuthenticationProvider;
import com.manjosh.practice.backend.dtos.CredentialsDto;
import com.manjosh.practice.backend.dtos.SignUpDto;
import com.manjosh.practice.backend.dtos.UserDto;
import com.manjosh.practice.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto){
        UserDto userDto =userService.login(credentialsDto);
        userDto.setToken(userAuthenticationProvider.createToken(userDto.getEmail()));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto user) {
        UserDto createdUser = userService.register(user);
        createdUser.setToken(userAuthenticationProvider.createToken(user.getEmail()));
        return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
    }
}
