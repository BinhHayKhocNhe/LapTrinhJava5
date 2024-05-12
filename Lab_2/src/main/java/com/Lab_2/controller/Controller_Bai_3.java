package com.Lab_2.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Lab_2.model.Product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/product")
public class Controller_Bai_3 {

	@GetMapping("/form")
	public String form(Model model) {
		model.addAttribute("product", new Product());
		return "Bai_3";
	}

	@PostMapping("/save")
	public String save(Model model, @RequestParam("name") String name, @RequestParam("price") double price) {
		Product product = new Product(name, price);
		model.addAttribute("product", product);
		return "Bai_3";
	}
	
	@ModelAttribute("items")
    public List<Product> getItems() {
		List<Product> productList = new ArrayList<>();
        productList.add(new Product("ASUS", 150));
        productList.add(new Product("HP", 120));
        productList.add(new Product("DELL", 250));
        productList.add(new Product("MAC", 712));
        productList.add(new Product("TIVI", 105));
        return productList;
    }
}
