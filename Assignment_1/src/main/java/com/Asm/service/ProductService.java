package com.Asm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Asm.Interface.ProductInterface;
import com.Asm.Model.Products;

@Service
@Transactional
public class ProductService {
	@Autowired
	private ProductInterface product;

	public List<Products> findAll() {
		return product.findAll();
	}

	public List<Products> getProductsByCategoryMP() {
		return product.findByCategoryIdCustom();
	}

	public List<Products> getProductsByCategoryTD() {
		return product.findByCategoryIdCustomTD();
	}
}
