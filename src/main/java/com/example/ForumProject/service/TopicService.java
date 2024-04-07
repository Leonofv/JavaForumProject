package com.example.ForumProject.service;

import com.example.ForumProject.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TopicService {

    Page<Topic> getPagedTopics(Pageable pageable);

    Topic saveTopic(Topic topic);

    void deleteTopic(Topic topic);

    Topic validateAndGetTopic(Long id);

    Topic getTopicById(Long topicId);
}
