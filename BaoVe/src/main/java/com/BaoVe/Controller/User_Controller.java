package com.BaoVe.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.BaoVe.DAO.UserDAO;
import com.BaoVe.Model.Users;
import com.BaoVe.Utils.SessionService;

@Controller
public class User_Controller {
	@Autowired
	private UserDAO dao = null;

	@Autowired
	private SessionService service = null;

	@GetMapping("/login")
	private String login(Model model) {
		service.removeSession("username");
		return "Login";
	}

	@PostMapping(value = "/login")
	private String login(Model model, @RequestParam("username") String username,
			@RequestParam("password") String password) {
		username = username.trim();
		password = password.trim();
		if (username.isEmpty() || password.isEmpty()) {
			model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu chưa được điền");
			System.out.println("Thiếu thông tin đăng nhập");
			return "Login";
		}
		Optional<Users> optionalUser = Optional.ofNullable(dao.findById(username).orElse(null));

		if (!optionalUser.isPresent()) {
			model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng");
			System.out.println("Người dùng không tồn tại");
			return "Login";
		}
		Users user = optionalUser.get();
		if (!user.getPass().equals(password)) {
			model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng");
			return "Login";
		}
		service.setSession("username", user.getId());
		return "redirect:/index";
	}

	@GetMapping("/index")
	private String index(Model model, @ModelAttribute("user") Users user) {
		loadTable(model);
		return "Home";
	}

	private void loadTable(Model model) {
		List<Users> users = dao.findAll();
		model.addAttribute("users", users);
	}

	@GetMapping(value = "/editUser/{id}")
	private String edit(Model model, @PathVariable("id") String id) {
		Users user = dao.findById(id).orElse(null);
		model.addAttribute("user", user);
		loadTable(model);
		return "Home";
	}

	@GetMapping(value = "/deleteUser/{id}")
	private String delete(Model model, @PathVariable("id") String id, @ModelAttribute("user") Users user) {
		dao.deleteById(id);
		model.addAttribute("message", "Xoá thành công!");
		System.out.println("Xóa thành công!");
		loadTable(model);
		return "Home";
	}

	@PostMapping(value = "/addUser")
	private String add(Model model, @ModelAttribute("user") Users user) {
		if (dao.existsById(user.getId())) {
			model.addAttribute("error", "ID đã tồn tại, vui lòng chọn ID khác!");
			System.out.println("ID đã tồn tại, vui lòng chọn ID khác!");
		} else {
			dao.save(user);
			model.addAttribute("message", "Thêm thành công!");
			System.out.println("Thêm thành công!");
		}
		loadTable(model);
		return "Home";
	}

	@PostMapping(value = "/updateUser")
	private String updateUser(Model model, @ModelAttribute("user") Users user) {
		if (!dao.existsById(user.getId())) {
			model.addAttribute("error", "ID không tồn tại, vui lòng chọn ID khác!");
			System.out.println("ID không tồn tại, vui lòng chọn ID khác!");
		} else {
			dao.update(user.getId(), user.getPass(), user.getFullname(), user.getEmail(), user.isAdmin());
			model.addAttribute("message", "Chỉnh sửa thành công!");
			System.out.println("Chỉnh sửa thành công!");
		}
		loadTable(model);
		return "Home";
	}

	@PostMapping(value = "/Find")
	private String findUser(Model model, @ModelAttribute("user") Users user,
			@RequestParam(value = "username", required = false) String username) {
		List<Users> users = new ArrayList<>();
		if (username != null && !username.isEmpty()) {
			user = dao.findById(username).orElse(null);
			if (user != null) {
				users.add(user);
			} else {
				model.addAttribute("error", "Không có user này!");
			}
		} else {
			model.addAttribute("error", "Vui lòng nhập user!");
			loadTable(model);
		}

		model.addAttribute("users", users);
		return "Home";
	}
}
