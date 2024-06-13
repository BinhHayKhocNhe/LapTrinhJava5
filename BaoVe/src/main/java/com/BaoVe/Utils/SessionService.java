package com.BaoVe.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class SessionService {
	@Autowired
	private HttpSession session;

	public <T> T getSession(String name, String defaultValue) {
		return (T) session.getAttribute(name) == null ? (T) defaultValue : (T) session.getAttribute(name);
	}

	public void setSession(String name, Object value) {
		session.setAttribute(name, value);
	}

	public void removeSession(String name) {
		session.removeAttribute(name);
	}
}
