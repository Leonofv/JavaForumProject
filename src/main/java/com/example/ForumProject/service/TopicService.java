package com.example.ForumProject.service;

import com.example.ForumProject.model.Topic;

import java.util.List;

public interface TopicService {

    List<Topic> getTopics();

    Topic saveTopic(Topic topic);

    void deleteTopic(Topic topic);

    Topic validateAndGetTopic(Long id);

    Topic getTopicById(Long topicId);
}
