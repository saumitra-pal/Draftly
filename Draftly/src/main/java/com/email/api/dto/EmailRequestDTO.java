package com.email.api.dto;

import lombok.Data;

@Data
public class EmailRequestDTO {
    private String emailContent;//actual content
    private String tone;
}
