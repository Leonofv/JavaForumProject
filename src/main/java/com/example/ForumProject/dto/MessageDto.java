package com.example.ForumProject.dto;

import java.time.ZonedDateTime;


public record MessageDto(Long id, MessageDto.UserDto user, MessageDto.TopicDto topic, String text, ZonedDateTime dateOfCreate) {

    public record UserDto(String username) {
    }

    public record TopicDto(Long topicId) {
    }
}