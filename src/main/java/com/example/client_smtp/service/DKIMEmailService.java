package com.example.client_smtp.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.stereotype.Service;

@Service
public class DKIMEmailService {

	public void sendEmailWithDKIM() throws IOException {
		// Configuración DKIM
		String selector = "selector1";
		String domain = "tudominio.com";
		String privateKeyPath = "/path/to/dkim_private.pem";
		byte[] privateKey = Files.readAllBytes(Paths.get(privateKeyPath));

		// Construir el email
		Email email = EmailBuilder
				.startingBlank()
				.from("Tu Nombre", "tu_email@" + domain)
				.to("Destinatario", "destinatario@ejemplo.com")
				.withSubject("Prueba de DKIM")
				.withPlainText("Contenido del correo")
				.buildEmail();

		// Configurar propiedades
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.port", "587");
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.starttls.enable", "true");

		// Configuración específica de DKIM
		props.setProperty("mail.smtp.dkim.selector", selector);
		props.setProperty("mail.smtp.dkim.domain", domain);
		props.setProperty("mail.smtp.dkim.privatekey", new String(privateKey));

		// Crear el Mailer
		Mailer mailer = MailerBuilder
				.withSMTPServer("smtp.gmail.com", 587, "tu_email@gmail.com", "tu_contraseña_de_aplicacion")
				.withTransportStrategy(TransportStrategy.SMTP_TLS)
				.buildMailer();

		// Enviar el correo
		mailer.sendMail(email);
	}
}