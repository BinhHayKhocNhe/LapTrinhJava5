package com.Asm.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.Asm.DAO.UserDAO;
import com.Asm.Model.Users;
import com.Asm.Utils.SessionService;

@Controller
public class User_Controller {
	@Autowired
	private UserDAO dao = null;

	@Autowired
	private SessionService session = null;

	private void loadUserData(Model model, Optional<String> key, Optional<Integer> p) {
		String keywords = key.orElse(session.getSession("keywordsUser", ""));
		session.setSession("keywordsUser", keywords);

		Pageable pageable = PageRequest.of(p.orElse(0), 10);
		Page<Users> page = dao.findByKeywords("%" + keywords + "%", "%" + keywords + "%", pageable);

		model.addAttribute("pageUser", page);
		model.addAttribute("keywordsUser", session.getSession("keywordsUser", ""));
		model.addAttribute("sessionUser", session.getSession("sessionUser", null));
		model.addAttribute("roleUser", session.getSession("roleUser", null));
	}

	@RequestMapping(value = "/User", method = { RequestMethod.GET, RequestMethod.POST })
	private String searchUser(Model model, @RequestParam("keywordsUser") Optional<String> key,
			@RequestParam("p") Optional<Integer> p) {
		Users user = new Users();
		if (user.getRole() == null) {
			user.setRole("Admin");
		}
		model.addAttribute("user", user);
		loadUserData(model, key, p);
		return "User";
	}

	@GetMapping(value = "/editUser/{ID_User}")
	private String edit(Model model, @PathVariable("ID_User") Long id,
			@RequestParam("keywordsUser") Optional<String> key, @RequestParam("p") Optional<Integer> p) {
		Users user = dao.findById(id).orElse(null);
		model.addAttribute("user", user);

		loadUserData(model, key, p);
		return "User";
	}

	@PostMapping(value = "/addUser")
	private String addUser(@RequestParam("keywordsUser") Optional<String> key, @RequestParam("p") Optional<Integer> p,
			@ModelAttribute("user") Users user, Model model) {

		if (user.getUsername() != null && dao.findByUsername(user.getUsername()) != null) {
			model.addAttribute("error", "Tài khoản không đúng định dạng!");
			System.out.println("Tài khoản đã được sử dụng hoặc không đúng định dạng!");
		} else {
			dao.addUser(user.getUsername(), user.getPassword(), user.getFullname(), user.getEmail(), user.getPhone(),
					user.isGender(), user.getBirthday(), user.getRole());
			model.addAttribute("message", "Thành công!");
			System.out.println("Thêm thành công!");
		}
		loadUserData(model, key, p);
		return "User";
	}

	@PostMapping(value = "/updateUser")
	private String updateUser(@RequestParam("keywordsUser") Optional<String> key,
			@RequestParam("p") Optional<Integer> p, @ModelAttribute("user") Users user, Model model) {
		if (user.getUsername() == null || dao.findByUsername(user.getUsername()) == null) {
			model.addAttribute("error", "Tài khoản không đúng định dạng!");
			System.out.println("Tài khoản chưa được sử dụng hoặc không đúng định dạng!");
		} else {
			System.out.println("ID" + user.getID_User());
			System.out.println("Pass" + user.getPassword());
			dao.updateUser(user.getID_User(), user.getUsername(), user.getPassword(), user.getFullname(),
					user.getEmail(), user.getPhone(), user.isGender(), user.getBirthday(), user.getRole());
			model.addAttribute("message", "Thành công!");
			System.out.println("Chỉnh sửa thông tin thành công!");
		}
		loadUserData(model, key, p);
		return "User";
	}

	@GetMapping(value = "/deleteUser/{ID_User}")
	private String deleteUser(@PathVariable("ID_User") Long id, @RequestParam("keywordsUser") Optional<String> key,
			@RequestParam("p") Optional<Integer> p, @ModelAttribute("user") Users user, Model model) {
		dao.deleteById(id);
		model.addAttribute("message", "Thành công!");
		System.out.println("Xóa thành công!");
		loadUserData(model, key, p);
		return "User";
	}
}
