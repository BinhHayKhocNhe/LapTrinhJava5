package com.Asm.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.Asm.Model.InvoiceDetails;

import jakarta.transaction.Transactional;

public interface InvoiceDetailDAO extends JpaRepository<InvoiceDetails, Long> {
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO InvoiceDetails (InvoiceID, ProductID, Quantity, Price) VALUES (?, ?, ?, ?)", nativeQuery = true)
	void insertDetail(Long InvoiceID, Long ProductID, int quantity, double Price);
}
