package com.example.ForumProject.dto;

import java.time.ZonedDateTime;
import java.util.List;
// удалить и топик и месендж за ненадобностью
public record UserDto (Long id, String firstName, String fatherName, String LastName, String username, String password, String role, List<MessageDto> messages, List<TopicDto> topics) {

    public record MessageDto(Long id, String text, ZonedDateTime dateOfCreate) {
    }

    public record TopicDto(Long id, String title) {
    }

}
