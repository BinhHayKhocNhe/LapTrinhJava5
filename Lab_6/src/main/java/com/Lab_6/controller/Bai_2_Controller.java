package com.Lab_6.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.Lab_6.DAO.ProductDAO;
import com.Lab_6.Entity.Product;
import com.Lab_6.utils.SessionService;

@Controller
public class Bai_2_Controller {
	@Autowired
	private ProductDAO dao = null;
	@Autowired
	private SessionService session = null;

	@GetMapping("/searchPage")
	private String Bai_2() {
		return "Bai_2";
	}

	@RequestMapping(value = "/searchPages", method = { RequestMethod.GET, RequestMethod.POST })
	private String search(Model model, @RequestParam("keywords") Optional<String> key,
			@RequestParam("p") Optional<Integer> p) {
		String keywords = key.orElse(session.getSession("keywords", ""));
		session.setSession("keywords", keywords);
		Pageable pageable = PageRequest.of(p.orElse(0), 3);
		Page<Product> page = dao.findByKeywords("%" + keywords + "%", pageable);
		model.addAttribute("page", page);
		model.addAttribute("keywords", session.getSession("keywords", ""));
		return "Bai_2";
	}
}
