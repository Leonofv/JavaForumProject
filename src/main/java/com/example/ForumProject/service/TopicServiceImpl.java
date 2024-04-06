package com.example.ForumProject.service;

import com.example.ForumProject.exception.TopicNotFoundException;
import com.example.ForumProject.model.Topic;
import com.example.ForumProject.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    @Override
    public List<Topic> getTopics() {
        return topicRepository.findAll();
    }

    @Override
    public Topic saveTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    @Override
    public Topic validateAndGetTopic(String topicId) {
        return topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic with this id not found"));
    }
}
