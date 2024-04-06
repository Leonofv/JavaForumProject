package com.example.ForumProject.dto;

import java.time.ZonedDateTime;
import java.util.List;

public record TopicDto(Long id, String title, TopicDto.UserDto user, List<MessageDto> messages) {

    public record MessageDto(Long id, String text, ZonedDateTime dateOfCreate) {
    }

    public record UserDto(Long id) {
    }

}