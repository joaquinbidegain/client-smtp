package com.example.client_smtp.service;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.Part;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {

	@Autowired
	private JavaMailSender mailSender;

	@Value("${email.signature.enabled}")
	private boolean isSignatureEnabled;

	public void sendCertifiedEmail(String to, String subject, String body) throws Exception {
	    MimeMessage message = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message, true);

	    helper.setTo(to);
	    helper.setSubject(subject);
	    helper.setText(body);

	    // Agregar firma solo si está habilitada
	    if (isSignatureEnabled) {
	        String digitalSignature = generateSignature(body);
	        ((Part) helper).addHeader("X-Digital-Signature", digitalSignature);
	    }

	    mailSender.send(message);
	}


	private String generateSignature(String message) throws Exception {
		// Cargar el KeyStore y obtener la clave privada
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		keyStore.load(getClass().getResourceAsStream("/keystore.p12"), "your-keystore-password".toCharArray());
		PrivateKey privateKey = (PrivateKey) keyStore.getKey("your-alias", "your-key-password".toCharArray());

		// Firmar el mensaje
		Signature signature = Signature.getInstance("SHA256withRSA");
		signature.initSign(privateKey);
		signature.update(message.getBytes());

		byte[] signedMessage = signature.sign();
		return Base64.getEncoder().encodeToString(signedMessage);
	}
}
