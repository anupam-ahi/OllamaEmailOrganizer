package org.example.ollamaemailsummarizer.service;

import lombok.extern.slf4j.Slf4j;
import org.example.ollamaemailsummarizer.utli.PromptLoader;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class OllamaService {
    private final PromptLoader promptLoader;
    private final ChatClient chatClient;

    public OllamaService(ChatClient.Builder chatClient, PromptLoader promptLoader) {
        this.chatClient = chatClient.build();
        this.promptLoader = promptLoader;
    }
    public String buildPromptWithEmail(String emailContent) throws Exception{
        String basePrompt = promptLoader.loadCategorizerPrompt();
        return basePrompt + "\n\nEmail:\n" + emailContent;
    }

    public String summarizeEmail(String emailContent) throws Exception {
        String basePrompt = promptLoader.loadSummarizerPrompt();
        basePrompt = basePrompt + "\n\nEmail:\n" + emailContent;
        return chatClient.prompt()
                .user(basePrompt)
                .call()
                .content();
    }

    public String llamaTest() throws IOException {
        return chatClient.prompt()
                .user(promptLoader.loadTestPrompt())
                .call()
                .content();
    }

    public String categorizeEmail(String emailContent) throws IOException {
        String basePrompt = promptLoader.loadCategorizerPrompt();
        basePrompt = basePrompt + "\n\nEmail:\n" + emailContent;
        System.out.println(basePrompt);
        return chatClient.prompt()
                .user(basePrompt)
                .call()
                .content();
    }
}
