package com.Asm.Model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
	private String phone;
	private String Address;
	private float Total;
	private String Fullname;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "CreateDate")
	private Date create_Date;

	@ManyToOne
	@JoinColumn(name = "ID_User")
	private Users users;

	@OneToMany(mappedBy = "invoices")
	List<invoice_details> details;
}
