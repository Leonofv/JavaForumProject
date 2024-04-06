package com.example.ForumProject.service;

import com.example.ForumProject.model.Topic;

import java.util.List;

public interface TopicService {

    List<Topic> getTopics();

    Topic saveTopic(Topic topic); // добавление топика

    Topic validateAndGetTopic(String id);
}
