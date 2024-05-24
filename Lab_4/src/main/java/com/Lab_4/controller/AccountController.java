package com.Lab_4.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Lab_4.utils.CookieService;
import com.Lab_4.utils.ParamService;
import com.Lab_4.utils.SessionService;

@Controller
public class AccountController {
	@Autowired
	CookieService cookieService;
	@Autowired
	ParamService paramService;
	@Autowired
	SessionService sessionService;

	@GetMapping("/")
	public String login1(Model model) {
		model.addAttribute("username", cookieService.getValueCookie("username"));
		model.addAttribute("password", cookieService.getValueCookie("password"));
		return "Login";
	}

	@PostMapping("/login")
	public String login2(Model model, @RequestParam("attach") MultipartFile attach) {
		String username = paramService.getStringParameter("username", "");
		String password = paramService.getStringParameter("password", "");
		boolean remember = paramService.getBoolean("remember", false);

		if (username.equals("poly") && password.equals("123")) {
			sessionService.setSession("username", username);
			if (remember) {
				cookieService.setCookie("username", username, 1);
				cookieService.setCookie("password", password, 1);

				File currentDirectory = new File(System.getProperty("user.dir"));
				File imgDirectory = new File(currentDirectory, "src/main/resources/img");
				if (!imgDirectory.exists()) {
					imgDirectory.mkdirs();
				}
				paramService.save(attach, imgDirectory.getAbsolutePath());

				model.addAttribute("username", cookieService.getValueCookie("username"));
				model.addAttribute("password", cookieService.getValueCookie("password"));
			} else {
				cookieService.removeCookie("username");
				cookieService.removeCookie("password");
			}
			model.addAttribute("message", "Login thành công !");
		} else {
			model.addAttribute("error", "Login thất bại !");
		}
		return "Login";
	}

}
