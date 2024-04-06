package com.example.ForumProject.controller;

import com.example.ForumProject.config.CustomUserDetails;
import com.example.ForumProject.dto.MessageDto;
import com.example.ForumProject.dto.MessageRequest;
import com.example.ForumProject.exception.MessageNotFoundException;
import com.example.ForumProject.exception.MessagePermissionException;
import com.example.ForumProject.mapper.MessageMapper;
import com.example.ForumProject.model.Message;
import com.example.ForumProject.model.User;
import com.example.ForumProject.service.MessageService;
import com.example.ForumProject.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final MessageService messageService;
    private final MessageMapper messageMapper;

    @PreAuthorize("isAuthenticated() && hasRole('ADMIN')")
    @DeleteMapping("/deleteMessage/{Id}") // работает
    public MessageDto deleteMessage(@PathVariable Long Id) {
        Message message = messageService.getMessageById(Id);
        if (message == null) {
            throw new MessageNotFoundException("Message not found");
        }
        messageService.deleteMessage(message);
        return messageMapper.toMessageDto(message);
    }

    @PreAuthorize("isAuthenticated() && hasRole('ADMIN')")
    @PutMapping("/editMessage/{Id}") // работает
    public MessageDto editMessage(@Valid @RequestBody MessageRequest messageRequest,
                                  @PathVariable Long Id) {
        Message message = messageService.getMessageById(Id);
        if (message == null) {
            throw new MessageNotFoundException("Message not found");
        }
        message.setText(messageRequest.getText());
        messageService.saveMessage(message);
        return messageMapper.toMessageDto(message);
    }
}
