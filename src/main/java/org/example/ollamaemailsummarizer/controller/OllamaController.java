package org.example.ollamaemailsummarizer.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequiredArgsConstructor
public class OllamaController {
    private final ChatClient chatClient;



    public OllamaController(ChatClient.Builder chatClient){
        this.chatClient = chatClient.build();
    }


    @GetMapping("")
    public String joke(){
//        String basePrompt = promptLoader.loadPrompt("prompt.txt");

        return chatClient.prompt()
                .user("Whats the collection of bahubali 2 when it released?")
                .call()
                .content();
    }




}
