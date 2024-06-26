package com.example.ForumProject.controller;

import com.example.ForumProject.security.CustomUserDetails;
import com.example.ForumProject.dto.MessageRequest;
import com.example.ForumProject.dto.MessageDto;
import com.example.ForumProject.exception.MessagePermissionException;
import com.example.ForumProject.exception.MessageNotFoundException;
import com.example.ForumProject.mapper.MessageMapper;
import com.example.ForumProject.model.Message;
import com.example.ForumProject.model.Topic;
import com.example.ForumProject.model.User;
import com.example.ForumProject.service.MessageService;
import com.example.ForumProject.service.TopicService;
import com.example.ForumProject.service.UserService;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final TopicService topicService;
    private final MessageMapper messageMapper;
    private final UserService userService;

    @GetMapping("/{topicId}")
    public List<MessageDto> getPagedMessagesByTopic(@PathVariable Long topicId,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Message> pageMessage = messageService.getPagedMessagesByTopicId(topicId, pageable);
        return pageMessage.stream()
                .map(messageMapper::toMessageDto)
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create/{topicId}")
    public MessageDto createMessageInCurrentTopic(@AuthenticationPrincipal CustomUserDetails currentUser,
                                                  @Valid @RequestBody MessageRequest messageRequest,
                                                  @PathVariable Long topicId) {
        User user = userService.validateAndGetUserByUsername(currentUser.getUsername());
        Topic topic = topicService.validateAndGetTopic(topicId);
        Message message = messageMapper.toMessage(messageRequest);
        message.setId(UUID.randomUUID().getMostSignificantBits());
        message.setTopic(topic);
        message.setUser(user);
        return messageMapper.toMessageDto(messageService.saveMessage(message));
    }

    @DeleteMapping("/delete/{id}")
    public MessageDto deleteMessage(@AuthenticationPrincipal CustomUserDetails currentUser,
                                    @PathVariable Long id) {
        User user = userService.validateAndGetUserByUsername(currentUser.getUsername());
        Message message = messageService.getMessageById(id);
        if (message == null) {
            throw new MessageNotFoundException("Message not found");
        }
        if (!message.getUser().equals(user)) {
            throw new MessagePermissionException("You don't have permission to delete this message");
        }
        messageService.deleteMessage(message);
        return messageMapper.toMessageDto(message);
    }

    @PutMapping("/edit/{Id}")
    public MessageDto editMessage(@AuthenticationPrincipal CustomUserDetails currentUser,
                                  @Valid @RequestBody MessageRequest messageRequest,
                                  @PathVariable Long Id) {
        User user = userService.validateAndGetUserByUsername(currentUser.getUsername());
        Message message = messageService.getMessageById(Id);
        if (message == null) {
            throw new MessageNotFoundException("Message not found");
        }
        if (!message.getUser().equals(user)) {
            throw new MessagePermissionException("You don't have permission to edit this message");
        }
        message.setText(messageRequest.getText());
        messageService.saveMessage(message);
        return messageMapper.toMessageDto(message);
    }
}
