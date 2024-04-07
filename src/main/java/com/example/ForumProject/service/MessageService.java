package com.example.ForumProject.service;

import com.example.ForumProject.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageService {

    Message saveMessage(Message message);

    Page<Message> getPagedMessagesByTopicId(Long topicId, Pageable pageable);

    void deleteMessage(Message message);

    Message getMessageById(Long messageId);
}
