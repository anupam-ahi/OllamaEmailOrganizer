package org.example.ollamaemailsummarizer.utli;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class PromptLoader {
    public String loadPrompt(String filename) throws IOException{
        ClassPathResource resource = new ClassPathResource("prompts/" + filename);
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

    }
}
