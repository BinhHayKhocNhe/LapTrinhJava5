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
import com.Asm.Utils.CartService;
import com.Asm.Utils.SessionService;

@Controller
public class Home_Controler {
	@Autowired
	private ProductDAO productDAO = null;

	private Pageable pageable = null;
	@Autowired
	private SessionService sessionService = null;

	@Autowired
	private CartService cartService = null;

	@GetMapping("/")
	private String index(Model model) {
		List<Products> topProducts = productDAO.selectRandom(4);
		List<Products> topSelling = productDAO.selectRandom(4);
		model.addAttribute("topProducts", topProducts);
		model.addAttribute("topSelling", topSelling);

		model.addAttribute("sessionUser", sessionService.getSession("sessionUser", null));
		model.addAttribute("roleUser", sessionService.getSession("roleUser", null));
		
		totalProducts(model);
		return "index";
	}

	@GetMapping("/DuocMyPham")
	private String duocMyPham(Model model, @RequestParam("p") Optional<Integer> p,
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

		model.addAttribute("sessionUser", sessionService.getSession("sessionUser", null));
		model.addAttribute("roleUser", sessionService.getSession("roleUser", null));
		
		totalProducts(model);
		return "DuocMyPham";
	}

	@GetMapping("/LienHe")
	private String contact(Model model) {
		model.addAttribute("sessionUser", sessionService.getSession("sessionUser", null));
		model.addAttribute("roleUser", sessionService.getSession("roleUser", null));
		
		totalProducts(model);
		return "LienHe";
	}

	@GetMapping("/Checkout")
	private String Checkout(Model model) {
		model.addAttribute("sessionUser", sessionService.getSession("sessionUser", null));
		model.addAttribute("roleUser", sessionService.getSession("roleUser", null));
		
		totalProducts(model);
		return "Checkout";
	}

	@GetMapping("/TrangDiem")
	private String TrangDiem(Model model, @RequestParam("p") Optional<Integer> p,
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

		model.addAttribute("sessionUser", sessionService.getSession("sessionUser", null));
		model.addAttribute("roleUser", sessionService.getSession("roleUser", null));
		
		totalProducts(model);
		return "TrangDiem";
	}

	@GetMapping(value = "/ProductDetail/{ProductID}")
	private String ProductDetail(Model model, @PathVariable("ProductID") Long id) {
		Products productDetail = productDAO.findByID(id);
		List<Products> topProducts = productDAO.selectRandom(4);
		model.addAttribute("productDetail", productDetail);
		model.addAttribute("topProducts", topProducts);

		model.addAttribute("sessionUser", sessionService.getSession("sessionUser", null));
		model.addAttribute("roleUser", sessionService.getSession("roleUser", null));
		
		totalProducts(model);
		return "ProductDetail";
	}

	private void totalProducts(Model model) {
		int totalProducts = cartService.getTotalProducts();
		model.addAttribute("sumProduct", totalProducts);
	}

}
