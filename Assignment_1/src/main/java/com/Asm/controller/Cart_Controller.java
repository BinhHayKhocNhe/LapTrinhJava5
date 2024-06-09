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
	private CartService cartService;
	 
	@Autowired
	private ProductDAO productDAO;
	 

	@GetMapping("/Cart")
	public String views() {
		return "Cart";
	}

	@GetMapping("/cart")
	public String viewCart(Model model) {
		model.addAttribute("cartItems", cartService.getItems());
		model.addAttribute("totalAmount", cartService.getTotalAmount());
		return "Cart";
	}
	
	@GetMapping("/cart-checkout")
	public String cartCheckout(Model model) {
		model.addAttribute("cartItems", cartService.getItems());
		model.addAttribute("totalAmount", cartService.getTotalAmount());
		return "Checkout";
	}
	
	

	@PostMapping("/add-cart/{productId}")
    public String addToCart(@PathVariable("productId") Long productId, @RequestParam("quantity") int quantity, Model model) {
        
        Products product = productDAO.findByID(productId);
        if (product != null) {
            cartService.addToCart(product, quantity);
        }
        return "redirect:/cart";
    }

	@PostMapping("/cart/update")
	public String updateQuantity(@RequestParam("productId") Long productId, @RequestParam("quantity") int quantity, Model model) {
		cartService.updateQuantity(productId, quantity);
		return "redirect:/cart";
	}

	@PostMapping("/cart/remove")
	public String removeFromCart(@RequestParam("productId") Long productId) {
		cartService.removeItem(productId);
		return "redirect:/cart";
	}

	@PostMapping("/cart/clear")
	public String clearCart() {
		cartService.clearCart();
		return "redirect:/cart";
	}
}