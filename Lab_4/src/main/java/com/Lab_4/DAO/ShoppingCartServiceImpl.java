package com.Lab_4.DAO;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.Lab_4.model.Item;
import com.Lab_4.untity.product;
import com.Lab_4.utils.ShoppingCartService;

@SessionScope
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
	private static HashMap<Integer, Item> cart = new HashMap<>();

	@Override
	public Item add(Integer id) {
		Item item = cart.get(id);
		if (item != null) {
			item.setQty(item.getQty() + 1);
		} else {
			item = product.item.get(id);
			item.setQty(1);
			cart.put(id, item);
		}
		return item;
	}

	@Override
	public void remove(Integer id) {
		cart.remove(id);
	}

	@Override
	public Item update(Integer id, int qty) {
		Item item = cart.get(id);
		// Update or remove product
		if (item != null) {
			// Kiểm tra và cập nhật số lượng sản phẩm
			int newQty = item.getQty() + 1;
			if (newQty <= 0) {
				// Xóa sản phẩm khỏi giỏ hàng nếu số lượng mới bằng hoặc nhỏ hơn 0
				cart.remove(id);
			} else if (newQty <= 10) {
				// Cập nhật số lượng nếu số lượng mới trong khoảng cho phép
				item.setQty(newQty);
			} else {
				// Đảm bảo số lượng không vượt quá 10
				item.setQty(10);
			}
		}
		return item;
	}

	@Override
	public void clear() {
		cart.clear();
	}

	@Override
	public int getCount() {
		return cart.values().stream().mapToInt(Item::getQty).sum();
	}

	@Override
	public double getAmount() {
		return cart.values().stream().mapToDouble(item -> item.getPrice() * item.getQty()).sum();
	}

	@Override
	public Map<Integer, Item> getProducts() {
		return cart;
	}

}
