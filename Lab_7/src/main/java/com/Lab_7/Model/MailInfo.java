package com.Lab_7.Model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailInfo {
	private String from, to, cc[], bcc[], subject, body, attachments[];
	private List<File> files = new ArrayList<>();

	public MailInfo(String to, String subject, String body) {
		this.from = "duongminhbinh999@gmail.com";
		this.to = to;
		this.subject = subject;
		this.body = body;
	}

}
