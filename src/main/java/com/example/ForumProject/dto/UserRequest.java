package com.example.ForumProject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {

    @Schema(example = "testUser")
    @NotBlank
    private String firstName;

    @Schema(example = "testUser")
    @NotBlank
    private String fatherName;

    @Schema(example = "testUser")
    @NotBlank
    private String lastName;

    @Schema(example = "testUser")
    @NotBlank
    private String username;

    @Schema(example = "testUser")
    @NotBlank
    private String password;
}
