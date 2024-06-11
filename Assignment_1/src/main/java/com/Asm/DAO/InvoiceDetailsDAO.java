package com.Asm.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Asm.Model.InvoiceDetails;

public interface InvoiceDetailsDAO extends JpaRepository<InvoiceDetails, Long> {

}
