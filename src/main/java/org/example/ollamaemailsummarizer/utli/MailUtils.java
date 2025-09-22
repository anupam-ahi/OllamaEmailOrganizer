package org.example.ollamaemailsummarizer.utli;

import jakarta.mail.*;
import jakarta.mail.internet.MimeMultipart;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Safelist;
import org.jsoup.select.Elements;

import java.io.IOException;

@Slf4j
public class MailUtils {
    /**
     * Sanitizes email body content by removing HTML tags, scripts, styles, and other unwanted elements
     * while preserving the readable text content in a human-friendly format.
     *
     * @param content The email content object (could be String, Multipart, etc.)
     * @return Clean, human-readable text content
     * @throws MessagingException if there's an issue processing the email content
     * @throws IOException if there's an issue reading the content
     */
    public static String sanitizeEmailBody(Object content) throws MessagingException, IOException {
        if (content == null) {
            return "";
        }

        String textContent = extractTextFromContent(content);

        if (textContent == null || textContent.trim().isEmpty()) {
            return "";
        }

        return cleanHtmlContent(textContent);
    }

    /**
     * Extracts text content from various email content types
     */
    private static String extractTextFromContent(Object content) throws MessagingException, IOException {
        if (content instanceof String) {
            return (String) content;
        } else if (content instanceof Multipart) {
            return extractTextFromMultipart((Multipart) content);
        } else {
            log.warn("Unsupported content type: {}", content.getClass().getName());
            return content.toString();
        }
    }

    /**
     * Extracts text from multipart email content
     */
    private static String extractTextFromMultipart(Multipart multipart) throws MessagingException, IOException {
        StringBuilder result = new StringBuilder();
        int count = multipart.getCount();

        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = multipart.getBodyPart(i);

            if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
                // Skip attachments
                continue;
            }

            if (bodyPart.isMimeType("text/plain")) {
                // Plain text content - append directly
                result.append(bodyPart.getContent().toString()).append("\n");
            } else if (bodyPart.isMimeType("text/html")) {
                // HTML content - will be cleaned later
                result.append(bodyPart.getContent().toString()).append("\n");
            } else if (bodyPart.isMimeType("multipart/*")) {
                // Nested multipart - recurse
                result.append(extractTextFromMultipart((Multipart) bodyPart.getContent()));
            }
        }

        return result.toString();
    }

    /**
     * Cleans HTML content using JSoup to extract human-readable text
     */
    private static String cleanHtmlContent(String htmlContent) {
        try {
            // Parse the HTML content
            Document doc = Jsoup.parse(htmlContent);

            // Remove unwanted elements
            removeUnwantedElements(doc);

            // Convert HTML to clean text while preserving some structure
            String cleanText = doc.text();

            // Post-process the text for better readability
            return postProcessText(cleanText);

        } catch (Exception e) {
            log.error("Error cleaning HTML content", e);
            // Fallback: try to clean with Safelist if parsing fails
            return Jsoup.clean(htmlContent, Safelist.none());
        }
    }

    /**
     * Removes common unwanted elements from email HTML
     */
    private static void removeUnwantedElements(Document doc) {
        // Remove script and style elements
        doc.select("script, style").remove();

        // Remove common email tracking elements
        doc.select("img[src*='tracking']").remove();
        doc.select("img[width='1'][height='1']").remove();

        // Remove common signature separators and disclaimers
        Elements signatures = doc.select("div:contains(--), div:contains(___), div:contains(This email)");
        signatures.remove();

        // Remove elements that commonly contain signatures or footers
        doc.select("div.signature, div.footer, div.disclaimer").remove();
        doc.select("div:contains(Confidential), div:contains(CONFIDENTIAL)").remove();
        doc.select("div:contains(unsubscribe), div:contains(UNSUBSCRIBE)").remove();

        // Remove empty elements
        doc.select("div:empty, p:empty, span:empty").remove();

        // Remove elements with only whitespace
        Elements elements = doc.select("*");
        for (Element element : elements) {
            if (element.text().trim().isEmpty() && !element.hasText()) {
                element.remove();
            }
        }
    }

    /**
     * Post-processes the cleaned text for better readability
     */
    private static String postProcessText(String text) {
        if (text == null) {
            return "";
        }

        return text
                // Remove excessive whitespace
                .replaceAll("\\s+", " ")
                // Remove excessive line breaks
                .replaceAll("\\n\\s*\\n\\s*\\n", "\n\n")
                // Trim leading and trailing whitespace
                .trim();
    }

    /**
     * Alternative method for more aggressive cleaning if needed
     * This preserves basic structure like paragraphs and line breaks
     */
    public static String sanitizeEmailBodyWithStructure(Object content) throws MessagingException, IOException {
        if (content == null) {
            return "";
        }

        String textContent = extractTextFromContent(content);

        if (textContent == null || textContent.trim().isEmpty()) {
            return "";
        }

        try {
            Document doc = Jsoup.parse(textContent);
            removeUnwantedElements(doc);

            // Preserve some structure by converting to text with formatting
            StringBuilder result = new StringBuilder();

            // Extract paragraphs and preserve line breaks
            Elements paragraphs = doc.select("p, div, br");
            for (Element p : paragraphs) {
                String text = p.text().trim();
                if (!text.isEmpty()) {
                    result.append(text).append("\n\n");
                }
            }

            // If no structured content found, fallback to simple text extraction
            if (result.length() == 0) {
                result.append(doc.text());
            }

            return postProcessText(result.toString());

        } catch (Exception e) {
            log.error("Error cleaning HTML content with structure", e);
            return Jsoup.clean(textContent, Safelist.none());
        }
    }






}
