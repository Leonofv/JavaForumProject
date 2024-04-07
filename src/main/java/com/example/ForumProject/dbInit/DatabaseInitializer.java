package com.example.ForumProject.dbInit;

import com.example.ForumProject.config.WebSecurityConfig;
import com.example.ForumProject.model.Message;
import com.example.ForumProject.model.Topic;
import com.example.ForumProject.model.User;
import com.example.ForumProject.service.MessageService;
import com.example.ForumProject.service.TopicService;
import com.example.ForumProject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final UserService userService;
    private final TopicService topicService;
    private final MessageService messageService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userService.getUsers().isEmpty()) {
            return;
        }

        USERS.forEach(user -> {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.saveUser(user);
        });

        for (Topic topic : TOPICS) {
            Optional<User> userOptional = userService.getUserByUsername("admin");
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                topic.setUser(user);
                Topic savedTopic = topicService.saveTopic(topic);

                Message firstMessage = new Message("First message for topic " + savedTopic.getTitle());
                firstMessage.setUser(user);
                firstMessage.setTopic(savedTopic);
                firstMessage.setDateOfCreate(ZonedDateTime.now());
                messageService.saveMessage(firstMessage);

                Optional<User> userOptional2 = userService.getUserByUsername("user");
                if (userOptional2.isPresent()) {
                    User user2 = userOptional2.get();
                    MESSAGES.forEach(message -> {
                        Message newMessage = new Message(message.getText());
                        newMessage.setUser(user2);
                        newMessage.setTopic(topic);
                        newMessage.setDateOfCreate(ZonedDateTime.now());
                        messageService.saveMessage(newMessage);
                    });
                }
            }
        }
    }

    private static final List<User> USERS = Arrays.asList(
            new User("admin", "Bob", "Michael", "Johnson", "admin", WebSecurityConfig.ADMIN),
            new User("user", "Emma", "Grace", "Davis", "user", WebSecurityConfig.USER)
    );

    private static final List<Topic> TOPICS = Arrays.asList(
            new Topic("Topic1"),
            new Topic("Topic2"),
            new Topic("Topic3"),
            new Topic("Topic4")
    );

    private static final List<Message> MESSAGES = Arrays.asList(
            new Message("New message1"),
            new Message("New message2")
    );
}

