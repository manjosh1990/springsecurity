package com.manjosh.practice.backend.service;

import com.manjosh.practice.backend.dtos.CredentialsDto;
import com.manjosh.practice.backend.dtos.SignUpDto;
import com.manjosh.practice.backend.dtos.UserDto;
import com.manjosh.practice.backend.entities.User;
import com.manjosh.practice.backend.exceptions.AppException;
import com.manjosh.practice.backend.mappers.UserMapper;
import com.manjosh.practice.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDto findByLogin(String login) {
        User user = userRepository.findByEmail(login)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        return userMapper.toUserDto(user);
    }

    public UserDto login(CredentialsDto credentialDto) {
      User user = userRepository.findByEmail(credentialDto.getUserName())
              .<AppException>orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
      if(passwordEncoder.matches(CharBuffer.wrap(credentialDto.getPassword()),user.getPassword())){
          return userMapper.toUserDto(user);
      }
      throw new AppException("Invalid password",HttpStatus.UNAUTHORIZED);
    }
    public UserDto register(SignUpDto userDto) {
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));

        User savedUser = userRepository.save(user);

        return userMapper.toUserDto(savedUser);
    }
}
