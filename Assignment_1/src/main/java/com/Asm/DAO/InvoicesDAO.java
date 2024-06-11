package com.Asm.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Asm.Model.Invoices;

public interface InvoicesDAO extends JpaRepository<Invoices, Long> {

}
