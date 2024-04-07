package com.example.ForumProject.controller;

import com.example.ForumProject.security.CustomUserDetails;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public List<TopicDto> getPagedTopics(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Topic> pageTopic = topicService.getPagedTopics(pageable);
        return pageTopic.stream()
                .map(topicMapper::toTopicDto)
                .collect(Collectors.toList());
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public TopicDto createTopicWithFirstMessage(@AuthenticationPrincipal CustomUserDetails currentUser,
                                                @Valid @RequestBody TopicRequest topicRequest) {
        User user = userService.validateAndGetUserByUsername(currentUser.getUsername());

        Topic topic = topicMapper.toTopic(topicRequest);
        topic.setId(UUID.randomUUID().getMostSignificantBits());
        topic.setUser(user);

        Message message = messageMapper.toMessage(topicRequest.getMessageRequest());
        message.setId(UUID.randomUUID().getMostSignificantBits());
        message.setTopic(topic);
        message.setUser(user);

        topic.getMessages().add(message);

        return topicMapper.toTopicDto(topicService.saveTopic(topic));
    }
}
