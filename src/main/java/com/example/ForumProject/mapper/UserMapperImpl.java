package com.example.ForumProject.mapper;

import com.example.ForumProject.dto.UserDto;
import com.example.ForumProject.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toUserDto(User user) {
        return null;
    }
}
