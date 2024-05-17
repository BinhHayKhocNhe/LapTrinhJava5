package com.Lab_3.model;

import java.util.List;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
	@NotBlank()
	private String name;

	@NotBlank
	@Email
	private String email;

	@DecimalMin(value = "0.0")
    @DecimalMax(value = "10.0")
	@NotNull
	private Double marks;

	@NotNull
	private Boolean gender = true;

	@NotBlank
	private String faculty;

	@NotEmpty
	private List<String> hobbies;
}
