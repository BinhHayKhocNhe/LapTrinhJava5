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

	@GetMapping("/GioHang.html")
	public String GioHang() {
		return "GioHang";
	}

	@GetMapping("/Cart.html")
	public String cart() {
		return "Cart";
	}

	@GetMapping("/LienHe.html")
	public String contact() {
		return "LienHe";
	}

	@GetMapping("/TrangDiem.html")
	public String TrangDiem() {
		return "TrangDiem";
	}

	@GetMapping("/SignIn")
	public String SignIn() {
		return "SignIn";
	}

	@GetMapping("/SignUp")
	public String SignUp() {
		return "SignUp";
	}
}
