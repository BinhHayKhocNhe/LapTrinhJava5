package com.Asm.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailInfo {
	private String from, to, subject, body;

	public MailInfo(String to, String subject, String body) {
		this.from = "duongminhbinh999@gmail.com";
		this.to = to;
		this.subject = subject;
		this.body = body;
	}
}
