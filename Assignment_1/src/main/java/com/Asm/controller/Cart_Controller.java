package com.Asm.controller;

import com.Asm.DAO.ProductDAO;
import com.Asm.Model.Products;
import com.Asm.Utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class Cart_Controller {

	@Autowired
	private CartService cartService = null;

	@Autowired
	private ProductDAO productDAO = null;

	@Autowired
	private SessionService sessionService = null;

	@GetMapping("/Cart")
	private String views(Model model) {
		model.addAttribute("sessionUser", sessionService.getSession("sessionUser", null));
		model.addAttribute("roleUser", sessionService.getSession("roleUser", null));
		
		totalProducts(model);
		return "Cart";
	}

	@GetMapping("/cart")
	private String viewCart(Model model) {
		model.addAttribute("cartItems", cartService.getItems());
		model.addAttribute("totalAmount", cartService.getTotalAmount());

		model.addAttribute("sessionUser", sessionService.getSession("sessionUser", null));
		model.addAttribute("roleUser", sessionService.getSession("roleUser", null));
		
		totalProducts(model);
		return "Cart";
	}

	@GetMapping("/cart-checkout")
	private String cartCheckout(Model model) {
		model.addAttribute("cartItems", cartService.getItems());
		model.addAttribute("totalAmount", cartService.getTotalAmount());

		model.addAttribute("sessionUser", sessionService.getSession("sessionUser", null));
		model.addAttribute("roleUser", sessionService.getSession("roleUser", null));
		
		totalProducts(model);
		return "Checkout";
	}

	@RequestMapping(value = "/add-cart/{productId}", method = { RequestMethod.GET, RequestMethod.POST })
	private String addToCart(@PathVariable("productId") Long productId,
			@RequestParam(name = "quantity", required = false, defaultValue = "1") int quantity, Model model) {
		Products product = productDAO.findByID(productId);
		if (product != null) {
			cartService.addToCart(product, quantity);
			totalProducts(model);
		}

		return "redirect:/cart";
	}

	@PostMapping("/cart/update")
	private String updateQuantity(@RequestParam("productId") Long productId, @RequestParam("quantity") int quantity,
			Model model) {
		cartService.updateQuantity(productId, quantity);
		return "redirect:/cart";
	}

	@PostMapping("/cart/remove")
	private String removeFromCart(@RequestParam("productId") Long productId, Model model) {
		cartService.removeItem(productId);
		return "redirect:/cart";
	}

	@PostMapping("/cart/clear")
	private String clearCart(Model model) {
		cartService.clearCart();
		return "redirect:/cart";
	}
	
	private void totalProducts(Model model) {
		int totalProducts = cartService.getTotalProducts();
		model.addAttribute("sumProduct", totalProducts);
	}
}