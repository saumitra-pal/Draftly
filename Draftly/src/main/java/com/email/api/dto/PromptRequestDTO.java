package com.email.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PromptRequestDTO {
    String model;
    List<PromptMessageDTO> messages;
    boolean stream;
}
