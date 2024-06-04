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
import com.Asm.Model.Products;
import com.Asm.Utils.SessionService;

@Controller
public class Product_Controller {
	@Autowired
	private ProductDAO dao = null;
	@Autowired
	private SessionService session = null;

	@RequestMapping(value = "/Product", method = { RequestMethod.GET, RequestMethod.POST })
	private String searchProduct(Model model, @RequestParam("keywordsProduct") Optional<String> key,
			@RequestParam("p") Optional<Integer> p) {

		String keywords = key.orElse(session.getSession("keywordsProduct", ""));
		session.setSession("keywordsProduct", keywords);

		Pageable pageable = PageRequest.of(p.orElse(0), 8);
		Page<Products> page = dao.findByKeywords("%" + keywords + "%", pageable);

		model.addAttribute("pageProduct", page);
		model.addAttribute("keywordsUser", session.getSession("keywordsUser", ""));
		return "Product";
	}
}
