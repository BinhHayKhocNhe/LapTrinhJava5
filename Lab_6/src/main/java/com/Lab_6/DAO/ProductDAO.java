package com.Lab_6.DAO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Lab_6.Entity.Product;
import com.Lab_6.Entity.Report;

public interface ProductDAO extends JpaRepository<Product, Integer> {
	@Query(value = "SELECT * FROM Products WHERE Price BETWEEN ?1 AND ?2", nativeQuery = true)
	List<Product> findByPrice(double minPrice, double maxPrice);

	@Query(value = "SELECT * FROM Products WHERE Name LIKE ?1", nativeQuery = true)
	Page<Product> findByKeywords(String keywords, Pageable pageable);

	@Query("SELECT new Report(o.categoryID, sum(o.price), count(o)) " + " FROM Product o " + " GROUP BY o.categoryID"
			+ " ORDER BY sum(o.price) DESC")
	List<Report> getInventoryByCategory();

	List<Product> findByPriceBetween(double minPrice, double maxPrice);

	Page<Product> findAllByNameLike(String keywords, Pageable pageable);
}
