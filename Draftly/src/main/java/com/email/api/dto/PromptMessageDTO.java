package com.email.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PromptMessageDTO {
    String role;   // "user", "system", "assistant"
    String content;
}
