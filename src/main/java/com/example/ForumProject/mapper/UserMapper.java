package com.example.ForumProject.mapper;

import com.example.ForumProject.dto.UserDto;
import com.example.ForumProject.model.User;

public interface UserMapper {

    UserDto toUserDto(User user);

}
