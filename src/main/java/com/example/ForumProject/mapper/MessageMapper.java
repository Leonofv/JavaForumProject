package com.example.ForumProject.mapper;

import com.example.ForumProject.dto.MessageRequest;
import com.example.ForumProject.dto.MessageDto;
import com.example.ForumProject.model.Message;

public interface MessageMapper {

    Message toMessage(MessageRequest messageRequest);

    MessageDto toMessageDto(Message message);
}
