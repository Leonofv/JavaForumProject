package com.example.ForumProject.mapper;

import com.example.ForumProject.dto.TopicRequest;
import com.example.ForumProject.dto.TopicDto;
import com.example.ForumProject.model.Message;
import com.example.ForumProject.model.Topic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicMapperImpl implements TopicMapper {

    @Override
    public Topic toTopic(TopicRequest topicRequest) {
        if (topicRequest == null) {
            return null;
        }
        return new Topic(topicRequest.getTitle());
    }

    @Override
    public TopicDto toTopicDto(Topic topic) {
        if (topic == null) {
            return null;
        }
        TopicDto.UserDto userDto = new TopicDto.UserDto(topic.getUser().getUsername());
        List<TopicDto.MessageDto> messages = topic.getMessages().stream().map(this::toTopicDtoMessageDto).toList();
        return new TopicDto(topic.getId(), topic.getTitle(), userDto, messages);
    }

    private TopicDto.MessageDto toTopicDtoMessageDto(Message message) {
        if (message == null) {
            return null;
        }
        return new TopicDto.MessageDto(message.getId(), message.getText(), message.getDateOfCreate());
    }
}
