package com.Asm.Interface;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Asm.Model.Products;

public interface ProductInterface extends JpaRepository<Products, Long> {
	@Query("SELECT p FROM Products p WHERE p.CategoryID = 'MP'")
	List<Products> findByCategoryIdCustom();
	
	@Query("SELECT p FROM Products p WHERE p.CategoryID = 'TD'")
	List<Products> findByCategoryIdCustomTD();
}
