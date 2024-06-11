package com.Asm.Utils;

import jakarta.mail.MessagingException;

public interface MailerService {
	void queue(String to, String subject, String body) throws MessagingException;
}

