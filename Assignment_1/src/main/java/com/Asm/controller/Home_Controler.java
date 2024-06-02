package com.Asm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.Asm.Model.Products;
import com.Asm.Model.Users;
import com.Asm.service.ProductService;
import com.Asm.service.UserService;

@Controller
public class Home_Controler {
	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;

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
	public String duocMyPham(Model model) {
		List<Products> productList = productService.getProductsByCategoryMP();
		model.addAttribute("products", productList);
		return "DuocMyPham";
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
	public String TrangDiem(Model model) {
		List<Products> productList = productService.getProductsByCategoryTD();
		model.addAttribute("products", productList);
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

	@GetMapping("/Admin.html")
	public String admin(Model model) {
		List<Users> users = userService.findAll();
		List<Products> products = productService.findAll();
		model.addAttribute("users", users);
		model.addAttribute("products", products);
		return "Admin";
	}

	//Di chuyển trang không lỗi
	@GetMapping("/edit/{ID_User}")
	public String getTrangDiem(@PathVariable("ID_User") String id) {
		System.out.println(id);
		return "redirect:/#!admin";
	}
}
