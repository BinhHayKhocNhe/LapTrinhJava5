package com.Lab_7.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Accounts {
	@Id
	private String Username;
	private String password;
	private String fullname;
	private String email;
	private String photo;
	private boolean Activated;
	private boolean Admin;
}
