package com.Asm.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Asm.Model.Invoices;

public interface InvoicesDAO extends JpaRepository<Invoices, Long> {

	@Query(value = "SELECT * FROM Invoices WHERE ID_User LIKE ?1 ORDER BY ID DESC", nativeQuery = true)
	List<Invoices> findByIdUser(Long id);
}
