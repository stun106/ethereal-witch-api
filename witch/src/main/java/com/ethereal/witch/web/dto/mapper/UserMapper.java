package com.ethereal.witch.web.dto.mapper;

import com.ethereal.witch.models.user.UserClient;
import com.ethereal.witch.web.dto.UserCreateDto;
import com.ethereal.witch.web.dto.UserResponseDto;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserClient toUser(UserCreateDto userCreateDto) {
        return new ModelMapper().map(userCreateDto, UserClient.class);
    }
    public static UserResponseDto toDto(UserClient user){
        return new ModelMapper().map(user, UserResponseDto.class);
     }
     public static List<UserResponseDto> toListDto(List<UserClient> userList){
        return userList.stream().map(UserMapper::toDto).collect(Collectors.toList());
     }
}