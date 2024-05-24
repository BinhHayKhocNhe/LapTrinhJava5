package com.Lab_4.untity;

import java.util.HashMap;
import java.util.Map;

import com.Lab_4.model.Item;

public class product {
	public static Map<Integer, Item> item = new HashMap<>();
	static {
		item.put(1, new Item(1, "Product 1", 1800, 2));
		item.put(2, new Item(2, "Product 2", 1200, 1));
		item.put(3, new Item(3, "Product 3", 300, 5));
		item.put(4, new Item(4, "Product 4", 1000, 3));
	}
}
