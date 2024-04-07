package com.example.ForumProject.mapper;

import com.example.ForumProject.dto.UserDto;
import com.example.ForumProject.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(user.getId(), user.getFirstName(), user.getFatherName(), user.getLastName(), user.getUsername(), user.getPassword(), user.getRole());
    }
}
