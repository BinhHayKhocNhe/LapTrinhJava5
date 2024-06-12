package com.Asm.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "InvoiceDetails")
public class InvoiceDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;
	private int Quantity;
	private double Price;

	@Transient
	private String productName;
	@Transient
    private double productPrice;
	
	@ManyToOne
	@JoinColumn(name = "InvoiceID")
	private Invoices invoices;

	@ManyToOne
	@JoinColumn(name = "ProductID")
	private Products products;
}
