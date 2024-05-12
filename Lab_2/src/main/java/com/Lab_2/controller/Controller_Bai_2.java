package com.Lab_2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/param")
public class Controller_Bai_2 {
	@GetMapping("/form")
	public String form() {
		return "Bai_2";
	}

	@PostMapping("/save/{x}")
	public String save(@PathVariable("x") String x, @RequestParam("y") String y, Model model) {
		model.addAttribute("x", x);
		model.addAttribute("y", y);
		return "Bai_2";
	}
}
