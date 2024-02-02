package com.manjosh.practice.backend.mappers;

import com.manjosh.practice.backend.dtos.SignUpDto;
import com.manjosh.practice.backend.dtos.UserDto;
import com.manjosh.practice.backend.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);
    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);

}
