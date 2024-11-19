package com.example.client_smtp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void testSendEmailWithoutSignature() {
        String to = "joaquinbidegain3@gmail.com"; // Cambia a un correo válido para probar
        String subject = "Test Email";
        String body = "This is a test email without a digital signature.";

        try {
            mailService.sendCertifiedEmail(to, subject, body);
            System.out.println("Email sent successfully to " + to);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
