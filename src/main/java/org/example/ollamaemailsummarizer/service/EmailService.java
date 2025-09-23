package org.example.ollamaemailsummarizer.service;

import jakarta.mail.*;
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
        inbox.open(Folder.READ_WRITE);
        Message[] messages = inbox.getMessages();
        if (messages.length > 0) {
            Message message = messages[messages.length - 3];
            log.info(message.getSubject());
            String emailBody = MailUtils.sanitizeEmailBodyWithStructure(message.getContent());
            log.info("Obtained the email body: {}" + emailBody);
            String summary = ollamaService.summarizeEmail(emailBody);
            log.info("Summary of the email content: {}" + summary);
            String category = ollamaService.categorizeEmail(summary);
            log.info("Category of the email: {}" + category);
            if (!category.equals("Ignore")) moveToFolder(message, category);
            inbox.close(true);
            return "Category: " + category + "\nSummary: " + summary;
        }
        return "Something went wrong...";
    }

    public void moveToFolder(Message message, String folderName) throws MessagingException {
        Folder destinationFolder = store.getFolder(folderName);
        if (!destinationFolder.exists()) {
            destinationFolder.create(Folder.HOLDS_MESSAGES);
        }
        Message[] messagesToMove = new Message[]{message};
        message.getFolder().copyMessages(messagesToMove, destinationFolder);
        message.setFlag(Flags.Flag.DELETED, true);
    }


}
