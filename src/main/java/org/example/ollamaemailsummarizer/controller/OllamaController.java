package org.example.ollamaemailsummarizer.controller;

import org.example.ollamaemailsummarizer.service.OllamaService;
import org.example.ollamaemailsummarizer.utli.PromptLoader;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class OllamaController {
    private final ChatClient chatClient;
    private final OllamaService ollamaService;

    private final PromptLoader promptLoader;

    public OllamaController(ChatClient.Builder chatClient, OllamaService ollamaService, PromptLoader promptLoader){
        this.chatClient = chatClient.build();
        this.ollamaService = ollamaService;
        this.promptLoader = promptLoader;
    }
    @GetMapping("")
    public String llamaTest() throws IOException {
        return chatClient.prompt()
                .user(promptLoader.loadTestPrompt())
                .call()
                .content();
    }

    @PostMapping("/classify")
    public String classifyEmailContent(@RequestBody String emailContent) throws Exception {
        return chatClient.prompt()
                .user(ollamaService.buildPromptWithEmail(emailContent))
                .call()
                .content();
    }




}
