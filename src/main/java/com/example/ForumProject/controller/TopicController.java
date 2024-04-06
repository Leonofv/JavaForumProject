package com.example.ForumProject.controller;

import com.example.ForumProject.dto.MessageRequest;
import com.example.ForumProject.dto.TopicRequest;
import com.example.ForumProject.dto.TopicDto;
import com.example.ForumProject.mapper.MessageMapper;
import com.example.ForumProject.mapper.TopicMapper;
import com.example.ForumProject.model.Message;
import com.example.ForumProject.model.Topic;
import com.example.ForumProject.model.User;
import com.example.ForumProject.service.TopicService;
import com.example.ForumProject.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.ForumProject.config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final UserService userService;
    private final TopicService topicService;
    private final TopicMapper topicMapper;
    private final MessageMapper messageMapper;

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping
    public List<TopicDto> getTopics() { // получаем список топиков
        List<Topic> topics = topicService.getTopics();
        return topics.stream()
                .map(topicMapper::toTopicDto)
                .collect(Collectors.toList());
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public TopicDto createTopicWithFirstMessage(
                                                @Valid @RequestBody TopicRequest topicRequest,
                                                @Valid @RequestBody MessageRequest messageRequest) {
        User user = userService.getUserById(1L);
        Topic topic = topicMapper.toTopic(topicRequest);
        Message message = messageMapper.toMessage(messageRequest);

        topic.setId(UUID.randomUUID().getMostSignificantBits());
        topic.setUser(user);

        message.setId(UUID.randomUUID().getMostSignificantBits());
        message.setTopic(topic);
        message.setUser(user);

        // Устанавливаем сообщение в список сообщений темы
        topic.setMessages(List.of(message));

        // Создаем тему в сервисе и возвращаем результат
        return topicMapper.toTopicDto(topicService.saveTopic(topic)); // ошибка скорее всего где-то тут при передаче в toTopicDto
    }
}
