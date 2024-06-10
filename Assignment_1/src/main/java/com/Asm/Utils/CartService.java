package com.Asm.Utils;

import com.Asm.Model.CartItem;
import com.Asm.Model.Products;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
public class CartService {
	private List<CartItem> items = new ArrayList<>();

	public void addToCart(Products product, int quantity) {
		CartItem existingItem = items.stream()
				.filter(item -> item.getProduct().getProductID().equals(product.getProductID())).findFirst()
				.orElse(null);

		if (existingItem != null) {
			existingItem.setQuantity(existingItem.getQuantity() + quantity);
		} else {
			items.add(new CartItem(product, quantity));
		}
	}

	public List<CartItem> getItems() {
		return items;
	}

	public void updateQuantity(Long productId, int quantity) {
		items.stream().filter(item -> item.getProduct().getProductID().equals(productId)).findFirst()
				.ifPresent(item -> item.setQuantity(quantity));
	}

	public void removeItem(Long productId) {
		items.removeIf(item -> item.getProduct().getProductID().equals(productId));
	}

	public void clearCart() {
		items.clear();
	}

	public double getTotalAmount() {
		return items.stream().mapToDouble(
				item -> item.getProduct().getPrice() * (100 - item.getProduct().getSale()) / 100 * item.getQuantity())
				.sum();
	}

	public int getTotalProducts() {
	    return items.size();
	}


}