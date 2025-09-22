package org.example.ollamaemailsummarizer.utli;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class PromptLoader {
    public String loadCategorizerPrompt() throws IOException {
        ClassPathResource resource = new ClassPathResource("prompts/CategorizerPrompt.txt");
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

    }
    public String loadTestPrompt() throws IOException{
        ClassPathResource resource = new ClassPathResource("/prompts/testPrompt.txt");
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }

    public String loadSummarizerPrompt() throws IOException {
        ClassPathResource resource = new ClassPathResource("prompts/SummarizerPrompt.txt");
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

    }
}
