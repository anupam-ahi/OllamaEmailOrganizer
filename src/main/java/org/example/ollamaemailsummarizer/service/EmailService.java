package org.example.ollamaemailsummarizer.service;

import jakarta.mail.Folder;
import jakarta.mail.MessagingException;
import jakarta.mail.Store;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    private final Store store;
    private final OllamaService ollamaService;

    public void emailCount() throws MessagingException{
        Map<String, Integer> map = new HashMap<>();
        Folder inbox = store.getFolder("inbox");
        Folder spam = store.getFolder("[Gmail]/Spam");
        inbox.open(Folder.READ_ONLY);
        log.info("No of messages: " + inbox.getMessageCount());
        map.put("No of messages: ", inbox.getMessageCount());
        log.info("No of Unread message: " + inbox.getUnreadMessageCount());
        map.put("No of Unread message: " , inbox.getUnreadMessageCount());
        log.info("No of messages in spam: " + spam.getMessageCount());
        map.put("No of messages in spam: " , spam.getMessageCount());
        log.info("No of unread messages in spam: " + spam.getUnreadMessageCount());
        map.put("No of unread messages in spam: ", spam.getUnreadMessageCount());
//        ollamaService.setOpenAIClient();
        inbox.close(true);
    }


}
