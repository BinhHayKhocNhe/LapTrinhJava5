package com.Asm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Home_Controler {
	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/header.html")
	public String header() {
		return "header";
	}

	@GetMapping("/footer.html")
	public String footer() {
		return "footer";
	}

	@GetMapping("/home.html")
	public String home() {
		return "home";
	}

	@GetMapping("/DuocMyPham.html")
	public String duocMyPham() {
		return "DuocMyPham";
	}

}
