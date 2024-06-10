package com.Asm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.Asm.DAO.UserDAO;
import com.Asm.Model.Users;
import com.Asm.Utils.CookieService;
import com.Asm.Utils.SessionService;

import java.util.Optional;

@Controller
public class SignIn_Controller {
	@Autowired
	private UserDAO dao = null;

	@Autowired
	private CookieService cookieService = null;

	@Autowired
	private SessionService sessionService = null;

	@GetMapping("/SignIn")
	private String SignIn(Model model) {
		model.addAttribute("user", cookieService.getValueCookie("username"));
		model.addAttribute("pass", cookieService.getValueCookie("password"));
		return "SignIn";
	}

	@PostMapping("/SignIn")
	private String processSignUp(Model model, @RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam(value = "remember", required = false) boolean remember) {

		// Xóa khoảng trắng ở đầu và cuối chuỗi
		username = username.trim();
		password = password.trim();

		if (username.isEmpty() || password.isEmpty()) {
			model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu chưa được điền");
			System.out.println("Thiếu thông tin đăng nhập");
			return "SignIn";
		}

		Optional<Users> optionalUser = Optional.ofNullable(dao.findByUsername(username));
		if (!optionalUser.isPresent()) {
			model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng");
			System.out.println("Người dùng không tồn tại");
			return "SignIn";
		}

		Users user = optionalUser.get();

		if (!user.getPassword().equals(password)) {
			model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng");
			System.out.println("Mật khẩu không đúng");
			return "SignIn";
		}

		if (remember == true) {
			cookieService.setCookie("username", username, 24);
			cookieService.setCookie("password", password, 24);

			sessionService.setSession("sessionUser", username);
			sessionService.setSession("roleUser", user.getRole());
		} else {
			cookieService.removeCookie("username");
			cookieService.removeCookie("password");

			sessionService.setSession("sessionUser", username);
			sessionService.setSession("roleUser", user.getRole());
		}
		System.out.println("Đăng nhập thành công");
		return "redirect:/";
	}

	@GetMapping("/logout")
	private String logout(Model model) {
		sessionService.removeSession("sessionUser");
		sessionService.removeSession("roleUser");
		return "redirect:/SignIn";
	}
}
