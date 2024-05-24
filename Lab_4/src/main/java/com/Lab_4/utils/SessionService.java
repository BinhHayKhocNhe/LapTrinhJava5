package com.Lab_4.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class SessionService {
	@Autowired
	HttpSession session;

	public <T> T getSession(String name) {
		return (T) session.getAttribute(name);
	}

	public void setSession(String name, Object value) {
		session.setAttribute(name, value);
	}

	public void removeSession(String name) {
		session.removeAttribute(name);
	}
}
