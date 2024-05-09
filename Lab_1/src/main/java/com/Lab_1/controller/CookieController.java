package com.Lab_1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Lab_1.entity.User;
import com.Lab_1.utils.Cookie.CookieUtils;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class CookieController {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private HttpSession session;

	@Autowired
	private ServletContext context;

	@RequestMapping("/")
	public String Index(Model model) {
		model.addAttribute("message", "Duong Minh Binh");
		return "index";
	}

	@RequestMapping("/Login")
	public String Login(Model model) {
		String username = CookieUtils.getCookie(request, "username");
		String password = CookieUtils.getCookie(request, "password");

		request.setAttribute("username", username);
		request.setAttribute("password", password);
		return "Login";
	}

	@PostMapping("/User")
	public String Users(Model model) {
		User user = new User();
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if (username.equals("") || password.equals("")) {
			return "Login";
		} else {
			CookieUtils.setCookie(response, "username", username, 1);
			CookieUtils.setCookie(response, "password", password, 1);

			user.setUsername(username);
			user.setPassword(password);
			System.out.println("Username: " + username);
			System.out.println("Password: " + password);

			session.setAttribute("user", user);
			model.addAttribute("user", user);
		}

		return "User";
	}
}
