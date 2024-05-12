package com.Lab_2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class Controller_Bai_4 {
	@RequestMapping("/a")
	public String m1(Model model) {
		model.addAttribute("message", "I come from a");
		return "Bai_4";
	}

	@RequestMapping("/b")
	public String m2(Model model) {
		model.addAttribute("message", "I come from b");
		return "forward:/a";
	}

	@RequestMapping("/c")
	public String m3(RedirectAttributes params, Model model) {
		params.addFlashAttribute("message", "I come from c");
		return "redirect:/a";
	}

	@ResponseBody
	@RequestMapping("/d")
	public String m4(Model model) {
		return "I come from d";
	}
}
