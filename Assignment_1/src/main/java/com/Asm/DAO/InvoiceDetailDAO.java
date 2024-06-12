package com.Asm.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.Asm.Model.invoice_details;

import jakarta.transaction.Transactional;

public interface InvoiceDetailDAO extends JpaRepository<invoice_details, Long> {
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO invoice_details (InvoiceID, ProductID, Quantity, Price) VALUES (?, ?, ?, ?)", nativeQuery = true)
	void insertDetail(Long InvoiceID, Long ProductID, int quantity, double Price);

//	@Query(value = "SELECT * FROM InvoiceDetails WHERE InvoiceID IN (SELECT ID FROM Invoices WHERE ID_User = ?1)", nativeQuery = true)
//    List<InvoiceDetails> findByInvoiceID(Long userID);
}
