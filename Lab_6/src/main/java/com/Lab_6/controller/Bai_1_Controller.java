package com.Lab_6.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Lab_6.DAO.ProductDAO;
import com.Lab_6.Entity.Product;

@Controller
public class Bai_1_Controller {
	@Autowired
	private ProductDAO dao;

	@GetMapping("/")
	private String Bai_1(Model model) {
		List<Product> items = dao.findAll();
		model.addAttribute("items", items);
		return "Bai_1";
	}

	@PostMapping("/search")
	private String search(Model model, @RequestParam("min") Optional<Double> min,
			@RequestParam("max") Optional<Double> max) {
		double minPrice = min.orElse(Double.MIN_VALUE);
		double maxPrice = max.orElse(Double.MAX_VALUE);
		List<Product> items = dao.findByPrice(minPrice, maxPrice);
		model.addAttribute("items", items);
		return "Bai_1";
	}
}
