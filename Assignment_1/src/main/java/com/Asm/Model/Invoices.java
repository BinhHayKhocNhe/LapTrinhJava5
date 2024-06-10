package com.Asm.Model;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Invoices")
public class Invoices {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;
	private double TotalAmount;
	private String PhoneNumber;
	private String Address;
	
	@ManyToOne
	@JoinColumn(name = "ID_User")
	private Users users;
	
	@OneToMany(mappedBy = "invoices")
	List<InvoiceDetails> details;
}
