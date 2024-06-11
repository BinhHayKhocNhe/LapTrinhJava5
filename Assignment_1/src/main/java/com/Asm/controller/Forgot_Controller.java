package com.Asm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Asm.DAO.UserDAO;
import com.Asm.Model.MailInfo;
import com.Asm.Model.Users;
import com.Asm.Utils.MailerService;
import com.Asm.Utils.SessionService;

@Controller
public class Forgot_Controller {

	@Autowired
	private UserDAO dao = null;
	@Autowired
	private MailerService service = null;

	@Autowired
	private SessionService sessionService = null;

	@GetMapping("/Forgot")
	private String showForgotPage(Model model) {
		String username = sessionService.getSession("username", null);
		Integer key = sessionService.getSession("key", null);

		model.addAttribute("username", username);
		model.addAttribute("key", key);

		return "Forgot";
	}

	private int numberRandom = (int) (Math.random() * 900000) + 100000;

	@PostMapping("/Forgot")
	private String submitForgot(Model model, @RequestParam(value = "username", required = false) String to,
			@RequestParam(value = "number", required = false) Integer number,
			@RequestParam(value = "newPassword", required = false) String newPassword) {
		Users user = dao.findByUsernameOrEmail(to);
		if (user == null) {
			model.addAttribute("error", "Tài khoản không tồn tại hoặc email không hợp lệ.");
			System.out.println("Tài khoản không tồn tại không hợp lệ.");
			numberRandom = (int) (Math.random() * 900000) + 100000;
			model.addAttribute("showInputForgot", false);
		} else {
			sessionService.setSession("username", user.getUsername());
			sessionService.setSession("key", number);
			try {
				MailInfo mailInfo = new MailInfo();
				mailInfo.setTo(to);
				mailInfo.setSubject("Quên mật khẩu");
				mailInfo.setBody("Mã của bạn là: " + numberRandom);
				service.queue(user.getEmail(), mailInfo.getSubject(), mailInfo.getBody());
			} catch (Exception e) {
				e.getMessage();
			}

		}
		return "redirect:/Forgot";
	}

	@GetMapping("/removeSessionForgot")
	public String getMethodName() {
		sessionService.removeSession("username");
		sessionService.removeSession("key");
		return "Forgot";
	}

	@PostMapping("/Change")
	public String changePassword(@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "number", required = false) Integer number,
			@RequestParam(value = "newPassword", required = false) String newPassword) {
		String sessionUsername = sessionService.getSession("username", null);

		if (sessionUsername != null && number != null && newPassword != null && newPassword.length() >= 8) {
			Integer sessionNumber = sessionService.getSession("key", null);
			if (number.equals(sessionNumber)) {
				dao.forgotPassword(newPassword, sessionUsername);
				sessionService.removeSession("username");
				sessionService.removeSession("key");
				System.out.println("Đổi mật khẩu thành công!");
			} else {
				System.out.println("Đổi mật khẩu thất bại!");
			}
		}
		return "SignIn";
	}

}
