package com.Lab_7.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Lab_7.DAO.AccountDAO;
import com.Lab_7.Model.Accounts;
import com.Lab_7.Utils.SessionService;

@Controller
public class Bai_5_Controller {
	@Autowired
	private AccountDAO dao = null;

	@Autowired
	private SessionService session = null;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/edit")
	public String edit() {
		return "edit";
	}

	@GetMapping("/about")
	public String about() {
		return "about";
	}

	@PostMapping("/login")
	public String login(Model model, @RequestParam("username") String username,
			@RequestParam("password") String password) {
		try {
			Accounts user = dao.getOne(username);
			if (!user.getPassword().equals(password)) {
				model.addAttribute("error", "Invalid password");
			} else {
				session.setSession("user", user);
				String uri = session.getSession("security-uri", "");
				if (uri != null) {
					session.removeSession("security-uri");
					return "redirect:" + uri;
				} else {
					model.addAttribute("message", "Login succeed");
					return "redirect:/edit"; // hoặc chuyển đến trang chủ hoặc trang khác
				}
			}
		} catch (Exception e) {
			model.addAttribute("error", "Invalid username");
		}
		return "login";
	}
}
