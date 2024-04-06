package com.example.ForumProject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MessageRequest {

    @Schema(example = "My message")
    @NotBlank
    private String text;
}
