package com.Asm.Model;

import java.util.List;

import jakarta.persistence.Column;
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
@Table(name = "Products")
public class Products {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ProductID;

	private String ProductTitle;
	private double Price;
	private String ImageURL;
	private int Quantity;
	
	private float Sale;
	private String Note;	
	
	
	@ManyToOne
	@JoinColumn(name = "CategoryID")
	private Categories categories ;
	
	@OneToMany(mappedBy = "products")
	List<invoice_details> orderDetails;
	
}
