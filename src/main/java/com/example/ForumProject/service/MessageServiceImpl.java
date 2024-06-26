package com.example.ForumProject.service;

import com.example.ForumProject.model.Message;
import com.example.ForumProject.model.Topic;
import com.example.ForumProject.repository.MessageRepository;
import com.example.ForumProject.repository.TopicRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final TopicRepository topicRepository;

    @Override
    public Page<Message> getPagedMessagesByTopicId(Long topicId, Pageable pageable) {
        Optional<Topic> optionalTopic = topicRepository.findById(topicId);
        if (optionalTopic.isPresent()) {
            return messageRepository.findByTopicId(topicId, pageable);
        } else {
            throw new EntityNotFoundException("Messages not found with topic id: " + topicId);
        }
    }

    @Override
    public Message getMessageById(Long messageId) {
        return messageRepository.getReferenceById(messageId);
    }

    @Override
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public void deleteMessage(Message message) {
        messageRepository.delete(message);
    }
}
