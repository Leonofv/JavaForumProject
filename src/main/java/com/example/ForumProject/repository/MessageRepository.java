package com.example.ForumProject.repository;

import com.example.ForumProject.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findMessagesByTopicId(Long topicId);

}
