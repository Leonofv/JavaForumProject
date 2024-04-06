package com.example.ForumProject.service;

import com.example.ForumProject.model.Message;

import java.util.List;

public interface MessageService {

    Message saveMessage(Message message); // добавление топика

    List<Message> getMessagesByTopicId(Long topicId);

    void deleteMessage(Message message);

    Message getMessageById(Long messageId);
}
