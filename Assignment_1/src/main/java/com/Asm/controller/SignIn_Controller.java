package com.Asm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Asm.DAO.UserDAO;
import com.Asm.Model.Users;

@Controller
public class SignIn_Controller {
	@Autowired
	private UserDAO dao = null;

	@PostMapping("/SignIn")
	public String processSignUp(Model model, @RequestParam("username") String username,
			@RequestParam("password") String password) {
		Users user = dao.findByUsername(username);
		if (username == "" || password == "") {
			model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu chưa được điền ");
			System.out.println("thiếu ");
			return "SignIn";
		}

		else if (user == null || !user.getPassword().equals(password)) {
			model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng");
			System.out.println("sai");
			return "SignIn";
		}
		System.out.println("ok");
		return "redirect:/";
	}
}
