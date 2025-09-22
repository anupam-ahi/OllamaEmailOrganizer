package org.example.ollamaemailsummarizer.controller;

import lombok.RequiredArgsConstructor;
import org.example.ollamaemailsummarizer.service.OllamaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class OllamaController {

    private final OllamaService ollamaService;

    @GetMapping("")
    public String llamaTest() throws IOException {
        return ollamaService.llamaTest();

    }

    @PostMapping("/categorize")
    public String classifyEmailContent(@RequestBody String emailContent) throws Exception {
        return ollamaService.categorizeEmail(emailContent);

    }

    @PostMapping("/summarize")
    public String summarizeEmail(@RequestBody String emailContent) throws Exception {
        return ollamaService.summarizeEmail(emailContent);

    }


}
