package com.email.api.service;

import com.email.api.dto.EmailRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface MailService {
    public String generateEmailReply(EmailRequestDTO req);
}
