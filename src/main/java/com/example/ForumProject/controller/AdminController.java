package com.example.ForumProject.controller;

import com.example.ForumProject.dto.MessageDto;
import com.example.ForumProject.dto.MessageRequest;
import com.example.ForumProject.dto.TopicDto;
import com.example.ForumProject.dto.TopicRequest;
import com.example.ForumProject.exception.MessageNotFoundException;
import com.example.ForumProject.exception.TopicNotFoundException;
import com.example.ForumProject.mapper.MessageMapper;
import com.example.ForumProject.mapper.TopicMapper;
import com.example.ForumProject.model.Message;
import com.example.ForumProject.model.Topic;
import com.example.ForumProject.service.MessageService;
import com.example.ForumProject.service.TopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final TopicService topicService;
    private final TopicMapper topicMapper;
    private final MessageService messageService;
    private final MessageMapper messageMapper;

    @PreAuthorize("isAuthenticated() && hasRole('ADMIN')")
    @PutMapping("/editTopic/{id}")
    public TopicDto editTopic(@Valid @RequestBody TopicRequest topicRequest,
                              @PathVariable Long id) {
        Topic topic = topicService.getTopicById(id);
        if (topic == null) {
            throw new TopicNotFoundException("Topic not found");
        }
        topic.setTitle(topicRequest.getTitle());
        topicService.saveTopic(topic);
        return topicMapper.toTopicDto(topic);
    }

    @PreAuthorize("isAuthenticated() && hasRole('ADMIN')")
    @DeleteMapping("/deleteTopic/{id}")
    public TopicDto deleteTopic(@PathVariable Long id) {
        Topic topic = topicService.getTopicById(id);
        if (topic == null) {
            throw new TopicNotFoundException("Topic not found");
        }
        List<Message> messageList = topic.getMessages();
        for (Message message : messageList) {
            messageService.deleteMessage(message);
        }
        topicService.deleteTopic(topic);
        return topicMapper.toTopicDto(topic);
    }

    @PreAuthorize("isAuthenticated() && hasRole('ADMIN')")
    @PutMapping("/editMessage/{id}")
    public MessageDto editMessage(@Valid @RequestBody MessageRequest messageRequest,
                                  @PathVariable Long id) {
        Message message = messageService.getMessageById(id);
        if (message == null) {
            throw new MessageNotFoundException("Message not found");
        }
        message.setText(messageRequest.getText());
        messageService.saveMessage(message);
        return messageMapper.toMessageDto(message);
    }

    @PreAuthorize("isAuthenticated() && hasRole('ADMIN')")
    @DeleteMapping("/deleteMessage/{id}")
    public MessageDto deleteMessage(@PathVariable Long id) {
        Message message = messageService.getMessageById(id);
        if (message == null) {
            throw new MessageNotFoundException("Message not found");
        }
        messageService.deleteMessage(message);
        return messageMapper.toMessageDto(message);
    }
}
