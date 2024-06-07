package com.Lab_7.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Lab_7.Model.Category;

public interface CategoryDAO extends JpaRepository<Category, String> {

}
