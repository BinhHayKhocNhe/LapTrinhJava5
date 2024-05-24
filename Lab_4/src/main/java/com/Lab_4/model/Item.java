package com.Lab_4.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
	private int id;
	private String name;
	private double price;
	private int qty = 1;

}
