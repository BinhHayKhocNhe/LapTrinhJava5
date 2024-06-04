package com.Asm.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.Asm.DAO.ProductDAO;
import com.Asm.DAO.UserDAO;
import com.Asm.Model.Products;
import com.Asm.Model.Users;
import com.Asm.Utils.SessionService;

@Controller
public class User_Controller {
	@Autowired
	private UserDAO dao = null;
	@Autowired
	private ProductDAO productDAO = null;

	@Autowired
	private SessionService session = null;

	@RequestMapping(value = "/User", method = { RequestMethod.GET, RequestMethod.POST })
	private String searchUser(Model model, @RequestParam("keywordsUser") Optional<String> key,
			@RequestParam("p") Optional<Integer> p) {

		String keywords = key.orElse(session.getSession("keywordsUser", ""));
		session.setSession("keywordsUser", keywords);

		Pageable pageable = PageRequest.of(p.orElse(0), 3);
		Page<Users> page = dao.findByKeywords("%" + keywords + "%", "%" + keywords + "%", pageable);

		model.addAttribute("pageUser", page);
		model.addAttribute("keywordsUser", session.getSession("keywordsUser", ""));
		return "User";
	}
}
