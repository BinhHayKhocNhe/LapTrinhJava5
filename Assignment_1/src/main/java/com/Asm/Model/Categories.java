package com.Asm.Model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Categories")
public class Categories {
	@Id
	private String CategoryID;
	private String CategoryName;
	private String Note;

	@OneToMany(mappedBy = "categories")
	List<Products> products;
}
