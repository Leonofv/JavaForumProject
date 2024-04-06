package com.example.ForumProject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TopicRequest {

    @Schema(example = "My title")
    @NotBlank
    private String title;
}
