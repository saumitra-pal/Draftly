package com.email.api.controller;

import com.email.api.dto.EmailRequestDTO;
import com.email.api.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "*")
public class MailController
{
    @Autowired
    private MailService emailService;
    @PostMapping("/generate")
    public ResponseEntity<String> generateEmail(@RequestBody EmailRequestDTO request){
        String response = emailService.generateEmailReply(request);
        return ResponseEntity.ok(response);

    }
}
