package org.example.ollamaemailsummarizer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ollamaemailsummarizer.utli.PromptLoader;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OllamaService {
    private final PromptLoader promptLoader;
    public String buildPromptWithEmail(String emailContent) throws Exception{
        String basePrompt = promptLoader.loadPrompt("prompt.txt");
        return basePrompt + "\n\nEmail:\n" + emailContent;
    }


}
