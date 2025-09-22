package org.example.ollamaemailsummarizer.service;

import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Store;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ollamaemailsummarizer.utli.MailUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    private final Store store;
    private final OllamaService ollamaService;


    public String emailCount() throws MessagingException {

        Folder inbox = store.getFolder("inbox");
        Folder spam = store.getFolder("[Gmail]/Spam");
        inbox.open(Folder.READ_ONLY);
        log.info("No of messages: {}", inbox.getMessageCount());
        log.info("No of Unread message: {}", inbox.getUnreadMessageCount());
        log.info("No of messages in spam: {}", spam.getMessageCount());
        log.info("No of unread messages in spam: {}", spam.getUnreadMessageCount());
        inbox.close(true);
        return "No of messages: " + inbox.getMessageCount() + "No of Unread message: " + inbox.getUnreadMessageCount() + "No of messages in spam: " + spam.getMessageCount();
    }

    public String readEmail() throws Exception {
        Folder inbox = store.getFolder("inbox");
        inbox.open(Folder.READ_ONLY);
        Message[] messages = inbox.getMessages();
        if (messages.length > 0) {
            Message message = messages[messages.length - 4];
            String emailBody = MailUtils.sanitizeEmailBodyWithStructure(message.getContent());
            System.out.println("Email body");
            inbox.close(true);
            String summary  = ollamaService.summarizeEmail(emailBody);
            return ollamaService.categorizeEmail(summary);
        }
        return "";
    }


}
