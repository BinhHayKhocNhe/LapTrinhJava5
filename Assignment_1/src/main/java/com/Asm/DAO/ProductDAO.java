package com.Asm.DAO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Asm.Model.Products;

public interface ProductDAO extends JpaRepository<Products, Long> {
	@Query("SELECT p FROM Products p WHERE p.CategoryID = 'MP'")
	Page<Products> findByCategoryIdCustom(Pageable pageable);

	@Query("SELECT p FROM Products p WHERE p.CategoryID = 'TD'")
	Page<Products> findByCategoryIdCustomTD(Pageable pageable);

	@Query(value = "SELECT * FROM Products WHERE product_title LIKE ?1", nativeQuery = true)
	Page<Products> findByKeywords(String tiltle, Pageable pageable);
	
	@Query(value = "SELECT * FROM Products WHERE ProductID = :id", nativeQuery = true)
	Products findByID(@Param("id") Long id);
}
