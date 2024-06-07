package com.Lab_7.Service;

import com.Lab_7.Model.MailInfo;

import jakarta.mail.MessagingException;

public interface MailerService {
	void send(MailInfo mail) throws MessagingException;

	void send(String to, String subject, String body) throws MessagingException;

	void queue(MailInfo mail) throws MessagingException;

	void queue(String to, String subject, String body) throws MessagingException;
}
