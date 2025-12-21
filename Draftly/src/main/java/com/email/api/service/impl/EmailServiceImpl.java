package com.email.api.service.impl;

import com.email.api.dto.EmailRequestDTO;
import com.email.api.dto.PromptMessageDTO;
import com.email.api.dto.PromptRequestDTO;
import com.email.api.service.MailService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailServiceImpl implements MailService {

    private final WebClient webClient;

    @Value("${ai.api.url}")
    private String aiApiUrl;
    @Value("${ai.api.key}")
    private String aiApiKey;
    @Value("${ai.api.model}")
    private String model;

    public EmailServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Override
    public String generateEmailReply(EmailRequestDTO req){
        //Build prompt
        String prompt = buildPrompt(req);
        //Craft prompt request structure
        List<PromptMessageDTO> messages= new ArrayList<>();
        messages.add(new PromptMessageDTO("user",prompt));
        PromptRequestDTO promptReq = new PromptRequestDTO(model, messages, false);

        //Async request for prompt
        String promptResponse = webClient.post()
                .uri(aiApiUrl)
                .header("Authorization","Bearer "+aiApiKey)
                .header("Content-Type","application/json")
                .bodyValue(promptReq)
                .retrieve().bodyToMono(String.class).block();

        //extract email reply and return
        return extractReply(promptResponse);
    }

    private String extractReply(String promptResponse) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(promptResponse);
            return rootNode.path("choices")
                    .get(0).path("message")
                    .path("content").asText();
        } catch (Exception e) {
            return "Error while processing prompt: "+ e.getMessage();
        }
    }

    private String buildPrompt(EmailRequestDTO req) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Generate an email reply for the following email content. Do not generate a subject line.");
        if(req.getTone()!=null && !req.getTone().isEmpty()){
            prompt.append("\nTone: ").append(req.getTone());
        }
        prompt.append("\nOriginal email: \n").append(req.getEmailContent());
        return prompt.toString();
    }
}
