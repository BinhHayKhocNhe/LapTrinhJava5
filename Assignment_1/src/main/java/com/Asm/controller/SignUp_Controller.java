package com.Asm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Asm.DAO.UserDAO;
import com.Asm.Model.Users;

@Controller
public class SignUp_Controller {
	@Autowired
	private UserDAO dao = null;

	@GetMapping("/SignUp")
	private String view(Model model) {

		Users user = new Users();
		model.addAttribute("user", user);

		return "SignUp";
	}

	@PostMapping("/SignUp")
	private String processSignUp(Model model, @ModelAttribute("user") Users user,
			@RequestParam("repassword") String repassword) {

		// Username existence check
		Users existingUser = dao.findByUsername(user.getUsername());
		if (existingUser != null) {
			model.addAttribute("error", "Tài khoản đã có người sử dụng!!!");
			return "SignUp";
		}
		// Basic field validations
		if (isEmpty(user.getFullname())) {
			model.addAttribute("error", "Họ tên đầy đủ của người dùng không được để trống");
			return "SignUp";
		} else if (isEmpty(user.getUsername())) {
			model.addAttribute("error", "Tên người dùng không được để trống");
			return "SignUp";
		} else if (isEmpty(user.getEmail())) {
			model.addAttribute("error", "Email không được để trống");
			return "SignUp";
		} else if (user.getBirthday() == null) {
			model.addAttribute("error", "Ngày sinh không được để trống");
			return "SignUp";
		} else if (isEmpty(user.getPassword())) {
			model.addAttribute("error", "Mật khẩu không được để trống");
			return "SignUp";
		} else if (isEmpty(repassword)) {
			model.addAttribute("error", "Nhập lại mật khẩu không được để trống");
			return "SignUp";
		} else if (isEmpty(user.getPhone())) {
			model.addAttribute("error", "Số điện thoại không được để trống");
			return "SignUp";
		}

		if (!isValidFullName(user.getFullname())) {
			model.addAttribute("error", "Tên của bạn phải là chữ và không được có kí tự đặc biệt!!");
			return "SignUp";
		}

		if (!isValidPhoneNumber(user.getPhone())) {
			model.addAttribute("error", "Số điện thoại nhập chưa đúng");
			return "SignUp";
		}
		// Password confirmation
		if (!user.getPassword().equals(repassword)) {
			model.addAttribute("error", "Mật khẩu nhập lại không trùng khớp");
			return "SignUp";
		}
//		// Email format validation
		if (!isValidEmail(user.getEmail())) {
			model.addAttribute("error", "Email bị sai định dạng");
			return "SignUp";
		}
		System.out.println("ok");
		user.setRole("User");

		dao.save(user);
//
//		Users user1 = new Users();
//		user1.setUsername(username);
//		user1.setEmail(email);
//		user1.setPassword(password);
//		user1.setPhone(phone); 
//		user1.setBirthday(date);
//		user1.setGender(true);
//		user1.setRole("User");
//		user1.setFullname("fullname");
//		
//
		// 3. Save the new user to the database (avoid unnecessary saveAndFlush())
		// dao.save(user1);

		// 4. Handle successful signup (e.g., redirect to login page, send confirmation

		return "redirect:/SignIn"; // Replace with appropriate redirect URL
	}

	private boolean isEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}

	private boolean isValidEmail(String email) {
		String regexPattern = "^[A-Za-z0-9._%+-]++@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
		return email.matches(regexPattern);

	}

	private boolean isValidPhoneNumber(String phone) {
		// Logic validate số điện thoại theo yêu cầu của bạn
		if (phone.startsWith("0") && phone.length() >= 10 && phone.length() <= 11) {
			return true;
		}
		return false;
	}

	private boolean isValidFullName(String fullName) {
		// Regular expression for validating full names (no numbers or special
		// characters)
		String regex = "[A-Za-zÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝÇçÑñ\\s]+";

		if (fullName.matches(regex)) {
			return true;
		} else {
			return false;
		}
	}

}
