package org.example.ollamaemailsummarizer.service;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatCompletion;
import com.openai.models.ChatCompletionCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ollamaemailsummarizer.utli.PromptLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
