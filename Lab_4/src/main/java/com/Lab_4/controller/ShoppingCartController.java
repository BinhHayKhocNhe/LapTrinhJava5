package com.Lab_4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.Lab_4.untity.product;

@Controller
public class ShoppingCartController {

	@GetMapping("/products")
	public String view(Model model) {
		model.addAttribute("items", product.item.values());
		return "Product";
	}
}
