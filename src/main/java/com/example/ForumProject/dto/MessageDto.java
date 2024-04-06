package com.example.ForumProject.dto;

import java.time.ZonedDateTime;

// тут мы как раз храним само сообщение и информацию о том, кто это сообщение сделал (нуно еще добавть о там где оно сделано (в каком топике))
public record MessageDto(Long id, MessageDto.UserDto user, MessageDto.TopicDto topic, String text, ZonedDateTime dateOfCreate) {

    public record UserDto(String username) {
    }

    public record TopicDto(Long topicId) {
    }
}