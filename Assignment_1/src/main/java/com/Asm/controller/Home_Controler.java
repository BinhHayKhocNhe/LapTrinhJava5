package com.Asm.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.Asm.DAO.ProductDAO;
import com.Asm.DAO.UserDAO;
import com.Asm.Model.Products;

@Controller
public class Home_Controler {
	@Autowired
	private ProductDAO productDAO = null;

	@Autowired
	private UserDAO userDAO = null;

	private Pageable pageable = null;

	@GetMapping("/")
	public String index(Model model) {
		List<Products> topProducts = productDAO.selectRandom(4);
		List<Products> topSelling = productDAO.selectRandom(4);
		model.addAttribute("topProducts", topProducts);
		model.addAttribute("topSelling", topSelling);
		return "index";
	}

	@GetMapping("/DuocMyPham")
	public String duocMyPham(Model model, @RequestParam("p") Optional<Integer> p,
			@RequestParam("sort") Optional<String> sort) {
		int currentPage = p.orElse(0);
		String currentSort = sort.orElse("ProductID");

		Sort sortOrder;
		switch (currentSort) {
		case "priceAsc":
			sortOrder = Sort.by(Sort.Direction.ASC, "Price");
			break;
		case "priceDesc":
			sortOrder = Sort.by(Sort.Direction.DESC, "Price");
			break;
		case "sale":
			sortOrder = Sort.by(Sort.Direction.DESC, "Sale");
			break;
		case "nameAsc":
			sortOrder = Sort.by(Sort.Direction.ASC, "ProductTitle");
			break;
		case "nameDesc":
			sortOrder = Sort.by(Sort.Direction.DESC, "ProductTitle");
			break;
		default:
			sortOrder = Sort.by(Sort.Direction.ASC, "ProductID");
			break;
		}

		pageable = PageRequest.of(currentPage, 6, sortOrder);
		Page<Products> page = productDAO.findByCategoryIdCustom(pageable);
		model.addAttribute("page", page);

		List<Products> list = productDAO.selectRandom(5);
		model.addAttribute("list", list);
		model.addAttribute("sort", currentSort);

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
	public String TrangDiem(Model model, @RequestParam("p") Optional<Integer> p,
			@RequestParam("sort") Optional<String> sort) {
		int currentPage = p.orElse(0);
		String currentSort = sort.orElse("ProductID");

		Sort sortOrder;
		switch (currentSort) {
		case "priceAsc":
			sortOrder = Sort.by(Sort.Direction.ASC, "Price");
			break;
		case "priceDesc":
			sortOrder = Sort.by(Sort.Direction.DESC, "Price");
			break;
		case "sale":
			sortOrder = Sort.by(Sort.Direction.DESC, "Sale");
			break;
		case "nameAsc":
			sortOrder = Sort.by(Sort.Direction.ASC, "ProductTitle");
			break;
		case "nameDesc":
			sortOrder = Sort.by(Sort.Direction.DESC, "ProductTitle");
			break;
		default:
			sortOrder = Sort.by(Sort.Direction.ASC, "ProductID");
			break;
		}

		pageable = PageRequest.of(currentPage, 6, sortOrder);
		Page<Products> productList = productDAO.findByCategoryIdCustomTD(pageable);
		model.addAttribute("products", productList);
		List<Products> list = productDAO.selectRandom(5);
		model.addAttribute("list", list);
		model.addAttribute("sort", currentSort);
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

	@GetMapping(value = "/ProductDetail/{ProductID}")
	public String ProductDetail(Model model, @PathVariable("ProductID") Long id) {
		Products productDetail = productDAO.findByID(id);
		List<Products> topProducts = productDAO.selectRandom(4);
		model.addAttribute("productDetail", productDetail);
		model.addAttribute("topProducts", topProducts);
		return "ProductDetail";
	}

}
