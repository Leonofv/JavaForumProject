package com.example.ForumProject.mapper;

import com.example.ForumProject.dto.MessageRequest;
import com.example.ForumProject.dto.MessageDto;
import com.example.ForumProject.model.Message;
import org.springframework.stereotype.Service;

@Service
public class MessageMapperImpl implements MessageMapper {

    @Override
    public Message toMessage(MessageRequest messageRequest) {
            if (messageRequest == null) {
                return null;
            }
            return new Message(messageRequest.getText());
    }

    @Override
    public MessageDto toMessageDto(Message message) {
        if (message == null) {
            return null;
        }
        MessageDto.TopicDto topicDto = new MessageDto.TopicDto(message.getTopic().getId()); // получаем id топика
        MessageDto.UserDto userDto = new MessageDto.UserDto(message.getUser().getUsername());
        return new MessageDto(message.getId(), userDto, topicDto, message.getText(), message.getDateOfCreate());
    }
}
