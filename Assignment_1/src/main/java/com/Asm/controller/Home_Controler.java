package com.Asm.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Asm.DAO.ProductDAO;
import com.Asm.Model.Products;


@Controller
public class Home_Controler {
	@Autowired
	private ProductDAO productDAO = null;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/DuocMyPham")
	public String duocMyPham(Model model, @RequestParam("p") Optional<Integer> p) {
		Pageable pageable = PageRequest.of(p.orElse(0), 6);
		Page<Products> page = productDAO.findByCategoryIdCustom(pageable);
		model.addAttribute("page", page);
		return "DuocMyPham";
	}

	@GetMapping("/Cart")
	public String cart() {
		return "Cart";
	}

	@GetMapping("/LienHe")
	public String contact() {
		return "LienHe";
	}

	@GetMapping("/TrangDiem")
	public String TrangDiem(Model model) {
		List<Products> productList = productDAO.findByCategoryIdCustomTD();
		model.addAttribute("products", productList);
		return "TrangDiem";
	}

	@GetMapping("/SignIn")
	public String SignIn() {
		return "SignIn";
	}


}
