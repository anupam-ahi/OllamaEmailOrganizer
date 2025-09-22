package org.example.ollamaemailsummarizer.controller;

import jakarta.mail.MessagingException;

import lombok.RequiredArgsConstructor;
import org.example.ollamaemailsummarizer.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void getEmailStats() throws MessagingException {
        emailService.emailCount();
    }


}
