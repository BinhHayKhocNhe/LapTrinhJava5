package com.Lab_4.utils;

import java.util.Map;

import com.Lab_4.model.Item;


public interface ShoppingCartService {
	Item add(Integer id);

	void remove(Integer id);

	Item update(Integer id, int qty);

	void clear();

	Map<Integer, Item> getProducts();

	int getCount();

	double getAmount();
}
