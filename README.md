# OllamaEmailOrganizer
Ollama agent running llama 3.2 model locally to organize inbox emails. 

This is a project to demonstrate the capabilities of the Ollama agent and the llama 3.2 model in organizing emails. The agent can read emails from an inbox, categorize them, and suggest actions such as replying, archiving, or deleting.
## Features
- Read emails from an inbox
- Summarize email content
- Categorize emails based on the summary into different labels in Inbox (e.g., Application, Success, Rejection)

## Requirements
- Java 17 or higher
- Ollama installed and configured to use the llama 3.1 model locally
- An email account with IMAP access, store the app password and email in environment variables

## Setup
1. Clone the repository using git clone
2. Navigate to the project directory
3. Set up environment variables for your email account
4. Build the project using Maven or your preferred build tool
5. Run the application

## Usage
1. Start the ollama model using: ollama run llama3.1
2. Run the spring boot application
3. Access the endpoints to organize your emails: http://localhost:8080/email/read (to read the email, summarize and move to the appropriate folder), http://localhost:8080 (to test if the model is working, returns a fake email with the summary and category)
4. The application will read emails from the inbox, summarize them, and categorize them based on the content.
5. Check your email inbox for categorized emails.

## Security Considerations
- Model runs locally, ensuring data privacy.
- Ensure your email credentials are stored securely and not hard-coded in the application.

## Future Improvements
- Parallelize email processing for faster performance.
- Text breakdown for handling larger emails.
- Using more capable models like GPT-4 for better understanding and categorization.
- Implement a user interface for easier interaction.

Note: This is a demo project and should be used with caution. Feel free to modify the prompts folder to suit your needs.