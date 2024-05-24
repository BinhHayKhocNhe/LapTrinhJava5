package com.Lab_4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Lab_4.DAO.ShoppingCartServiceImpl;
import com.Lab_4.utils.ParamService;
import com.Lab_4.utils.SessionService;;

@Controller
public class CartController {
	@Autowired
	ShoppingCartServiceImpl cart;
	@Autowired
	ParamService param;

	@Autowired
	SessionService session;

	@GetMapping("/view")
	public String getCart(Model model) {
		model.addAttribute("items", cart.getProducts());
		model.addAttribute("amount", cart.getAmount());

		Integer cartQuantity = (Integer) session.getSession("cartQuantity");
		// Thêm cartQuantity vào model để hiển thị trong view
		if (cartQuantity != null) {
			model.addAttribute("cartQuantity", cartQuantity);
		}
		return "Cart";
	}

	@GetMapping("/add/{itemId}")
	public String addToCart(@PathVariable("itemId") int id) {
		cart.add(id);
		session.setSession("cartQuantity", cart.getCount());
		return "redirect:/view";
	}

	@PostMapping("/update/{itemId}")
	public String updateCart(@PathVariable("itemId") int id, @RequestParam("quantity") int quantity) {
	    cart.update(id, quantity);
	    session.setSession("cartQuantity", cart.getCount());
	    return "redirect:/view";
	}


	@GetMapping("remove/{itemId}")
	public String removeFromCart(@PathVariable("itemId") int id, Model model) {
		cart.remove(id);
		session.setSession("cartQuantity", cart.getCount());
		return "redirect:/view";
	}

	@GetMapping("clear")
	public String clearCart(Model model) {
		cart.clear();
		session.setSession("cartQuantity", cart.getCount());
		return "redirect:/view";
	}
}
