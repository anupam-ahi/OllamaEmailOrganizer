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
    public String getEmailStats() throws MessagingException {
        return emailService.emailCount();
    }

    @GetMapping("/read")
    public String getFirstEmail() throws Exception {
        return emailService.readEmail();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void clearEmails() throws MessagingException {
        emailService.deleteEmail();
    }


}
