package com.example.client_smtp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.client_smtp.service.MailService;

@RestController
@RequestMapping("/api/emails")
public class MailController {

	@Autowired
	private MailService mailService;

	@PostMapping("/send")
	public String sendCertifiedEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String body) {
		try {
			mailService.sendCertifiedEmail(to, subject, body);
			return "Email sent successfully!";
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed to send email: " + e.getMessage();
		}
	}
}
