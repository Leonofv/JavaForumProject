package com.example.ForumProject.mapper;

import com.example.ForumProject.dto.TopicRequest;
import com.example.ForumProject.dto.TopicDto;
import com.example.ForumProject.model.Topic;

public interface TopicMapper {

    Topic toTopic(TopicRequest topicRequest);

    TopicDto toTopicDto(Topic topic);
}
